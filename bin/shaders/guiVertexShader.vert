#version 140

in vec2 position;

out vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform float textureScaleX;
uniform float textureScaleY;

void main(void){
	//TODO: fix this
	gl_Position = (transformationMatrix * vec4(position, 0.0, 1.0)) * vec4(4.0, -4.0, 0.0, 1.0) + vec4(-2.0, 2.0, 0.0, 1.0);
	textureCoords = vec2(((position.x+1.0)/2.0) * textureScaleX, ((position.y+1.0)/2.0) * textureScaleY);
	//gl_Position = vec4(position * vec2(2.0, -2.0), 0.0, 1.0);
}