package com.liberator.wisoliter.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.utils.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun parsePolygonFromJson(jsonPath: String, name: String): Polygon {
    val json = Json()
    val root = json.fromJson(Root::class.java, Gdx.files.internal(jsonPath))

    val rigidBody = root.rigidBodies.find { it.name == name }
        ?: throw IllegalArgumentException("RigidBody with name '$name' not found in $jsonPath")

    // Створюємо FloatArray безпосередньо
    val vertices = FloatArray(rigidBody.vertices.size * 2) { i ->
        if (i % 2 == 0) rigidBody.vertices[i / 2].x else rigidBody.vertices[i / 2].y
    }

    return Polygon(vertices)
}

@Serializable
data class Vertex(
    @SerialName("x") val x: Float = 0f,
    @SerialName("y") val y: Float = 0f,
)

@Serializable
data class RigidBody(
    @SerialName("name") val name: String = "",
    @SerialName("vertices") val vertices: List<Vertex> = emptyList()
)

@Serializable
data class Root(
    @SerialName("rigidBodies") val rigidBodies: List<RigidBody> = emptyList()
)