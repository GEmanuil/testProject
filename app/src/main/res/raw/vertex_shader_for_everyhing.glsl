attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TexCoord;

uniform mat4 u_Matrix;

void main()
{
    gl_Position = u_Matrix * a_Position;
    v_TexCoord = a_TextureCoordinates;
}