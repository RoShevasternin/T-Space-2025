#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;

uniform float u_percentages[10];  // Максимум 10 сегментів
uniform vec4 u_colors[10];        // Максимум 10 кольорів
uniform int u_segmentCount;       // Кількість сегментів
varying vec4 v_color;             // Отриманий колір з вершинного шейдера

void main() {
    float radius = 0.5;

    // Нормалізуємо текстурні координати до діапазону [-1, 1]
    vec2 normalizedCoord = v_texCoord * 2.0 - 1.0;
    float dist = length(normalizedCoord);

    // Якщо координата всередині кола
    if (dist < radius) {
        float angle = atan(normalizedCoord.y, normalizedCoord.x);
        angle += 3.14159265359 / 1.0;  // Перемістимо початок зверху

        // Якщо кут менший за 0, додаємо 360 градусів (2π радіан)
        if (angle < 0.0) {
            angle += 2.0 * 3.14159265359;
        }

        // Обчислюємо який сегмент повинен бути намальований
        float cumulativePercentage = 0.0;
        for (int i = 0; i < u_segmentCount; i++) {
            cumulativePercentage += u_percentages[i];
            float segmentLimit = cumulativePercentage * 2.0 * 3.14159265359 / 100.0;

            if (angle < segmentLimit) {
                gl_FragColor = u_colors[i] * v_color;
                return;
            }
        }
    } else {
        discard;  // Залишаємо прозорість поза межами кола
    }
}
