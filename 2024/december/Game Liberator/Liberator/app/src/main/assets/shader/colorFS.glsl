#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
varying vec4 v_color;

uniform sampler2D u_texture;
uniform float u_percentage; // Відсоток (0.0 - 1.0)
uniform vec4 u_targetColor; // Цільовий колір (FF4A4A)

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);

    // Обчислюємо, наскільки текстура наближається до цільового кольору
    float gradientBoundary = 1.0 - u_percentage;

    // Інтерполяція кольору
    vec4 finalColor;
    if (v_texCoords.x >= gradientBoundary) {
        // Градієнт: від текстури до цільового кольору
        float influence = (v_texCoords.x - gradientBoundary) / u_percentage;
        finalColor = mix(texColor * v_color, u_targetColor, influence);
    } else {
        // Початковий колір текстури
        finalColor = texColor * v_color;
    }

    // Поступовий перехід текстури до цільового кольору в залежності від відсотка
    finalColor = mix(finalColor, u_targetColor, u_percentage);

    gl_FragColor = vec4(finalColor.rgb, texColor.a * v_color.a);
}
