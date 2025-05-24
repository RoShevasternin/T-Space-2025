#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color; // Колір, переданий з вершинного шейдера
varying vec2 v_texCoords; // Текстурні координати

uniform sampler2D u_texture; // Текстура
uniform float u_time; // Час для анімації

// Функція для створення псевдовипадкового числа
float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

// Простий шум
float noise(vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

void main() {
    vec2 st = v_texCoords;

    // Спотворення текстурних координат для ефекту полум'я
    float waveX = sin(u_time * 3.0 + st.y * 12.0) * 0.015; // Горизонтальні хвилі
    float waveY = cos(u_time * 2.0 + st.x * 12.0) * 0.015; // Вертикальні хвилі

    // Коливання форми через шум
    float distortion = noise(st * 5.0 + u_time * 0.8) * 0.03;

    // Підсумкове спотворення текстурних координат
    vec2 uv = st + vec2(waveX, waveY) + distortion;

    // Вибір кольору текстури
    vec4 texColor = texture2D(u_texture, uv);

    // Збереження оригінального кольору та прозорості
    gl_FragColor = texColor * v_color;
}






//#ifdef GL_ES
//precision mediump float;
//#endif
//
//varying vec4 v_color; // Колір, переданий з вершинного шейдера
//varying vec2 v_texCoords; // Текстурні координати
//
//uniform sampler2D u_texture; // Текстура
//uniform float u_time; // Час для анімації
//
//// Функція для створення псевдовипадкового числа
//float random(vec2 st) {
//    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);
//}
//
//// Функція для створення простого шуму
//float noise(vec2 st) {
//    vec2 i = floor(st);
//    vec2 f = fract(st);
//
//    float a = random(i);
//    float b = random(i + vec2(1.0, 0.0));
//    float c = random(i + vec2(0.0, 1.0));
//    float d = random(i + vec2(1.0, 1.0));
//
//    vec2 u = f * f * (3.0 - 2.0 * f);
//
//    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
//}
//
//void main() {
//    vec2 st = v_texCoords;
//
//    // Динамічне спотворення текстурних координат для імітації хвиль
//    float waveX = sin(st.y * 15.0 + u_time * 2.0) * 0.02;
//    float waveY = cos(st.x * 15.0 + u_time * 2.0) * 0.02;
//
//    // Додаткове зміщення текстури через шум
//    float distortion = noise(st * 5.0 + u_time * 0.5) * 0.03;
//
//    vec2 uv = st + vec2(waveX, waveY) + distortion;
//
//    // Зразок текстури
//    vec4 texColor = texture2D(u_texture, uv);
//
//    // Підсумковий колір
//    gl_FragColor = texColor * v_color;
//}





//#ifdef GL_ES
//precision mediump float;
//#endif
//
//varying vec4 v_color; // Колір, переданий з вершинного шейдера
//varying vec2 v_texCoords; // Текстурні координати
//
//uniform sampler2D u_texture; // Текстура
//uniform float u_time; // Час для анімації
//
//void main() {
//    vec2 st = v_texCoords;
//
//    // Спотворення текстурних координат на основі синусоїдальних хвиль
//    float waveX = sin(u_time * 3.0 + st.y * 10.0) * 0.02; // Горизонтальні хвилі
//    float waveY = cos(u_time * 2.5 + st.x * 10.0) * 0.02; // Вертикальні хвилі
//
//    // Динамічне коливання текстури
//    vec2 jellyEffect = vec2(waveX, waveY);
//
//    // Спотворені текстурні координати
//    vec2 uv = st + jellyEffect;
//
//    // Отримання текстурного кольору
//    vec4 texColor = texture2D(u_texture, uv);
//
//    // Підсумковий колір з урахуванням кольору актора
//    gl_FragColor = texColor * v_color;
//}
