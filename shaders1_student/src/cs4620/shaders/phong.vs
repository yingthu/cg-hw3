#version 120

// uniforms -- same value is used for every vertex in model
uniform mat4 un_Projection;
uniform mat4 un_ModelView;
uniform mat3 un_NormalMatrix;

uniform vec3 un_AmbientColor;
uniform vec3 un_DiffuseColor;
uniform vec3 un_SpecularColor;
uniform float un_Shininess;

uniform vec3 un_LightPositions[16];
uniform vec3 un_LightIntensities[16];
uniform vec3 un_LightAmbientIntensity;

// vertex attributes -- distinct value used for each vertex
attribute vec3 in_Vertex;
attribute vec3 in_Normal;

// TODO: (Shaders 1 Problem 1) Add any varying variables here
varying vec3 ambient_Color;
varying	vec3 diffuse_Color;
varying	vec3 specular_Color;

void main(void)
{
	// TODO: (Shaders 1 Problem 1) Implement the vertex shader for per-pixel
	// Blinn-Phong here
	// Initialize
	ambient_Color = vec3(0,0,0);
	diffuse_Color = vec3(0,0,0);
	specular_Color = vec3(0,0,0);
	
	// Interpolated normal
	vec3 interp_Normal = normalize(un_NormalMatrix * in_Normal);
	// Vertex position in eye space
	vec4 pos_Vertex = un_ModelView * vec4(in_Vertex, 1);
	
	// La = ka Ia
	ambient_Color = un_AmbientColor * un_LightAmbientIntensity;
	
	// Ld = kd Id max(n.l,0); Ls = ks Is max(n.h,0)^n
	for (int i = 0; i < 16; i++)
	{
		vec3 l = normalize(un_LightPositions[i] - pos_Vertex.xyz);
		float nl = dot(interp_Normal,l);
		if (nl > 0)
		{
			diffuse_Color += un_DiffuseColor * un_LightIntensities[i] * nl;
			vec3 v = normalize(vec3(0,0,0) - pos_Vertex.xyz);
			vec3 h = normalize(v+l);
			specular_Color += un_SpecularColor * un_LightIntensities[i] * pow(max(dot(interp_Normal,h),0),un_Shininess);
		}
	}
	gl_Position = un_Projection * un_ModelView * vec4(in_Vertex, 1);
}

