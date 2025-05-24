attribute vec4 a_position;
attribute vec2 a_texCoord0;
attribute vec4 a_color;

uniform mat4 u_projTrans;

varying vec2 v_texCoord;
varying vec4 v_color;

void main() {
    v_texCoord = a_texCoord0;
    v_color = a_color;
    gl_Position = u_projTrans * a_position;
}
