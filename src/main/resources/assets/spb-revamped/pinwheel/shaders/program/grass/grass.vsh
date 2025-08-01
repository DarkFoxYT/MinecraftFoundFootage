#version 460
#include veil:camera

layout(location = 0) in vec3 Position;
layout(location = 1) in vec3 Normal;

layout (std430, binding = 0) buffer MyBuffer {
    vec3 position[];
} myBuffer;

uniform float GameTime;
uniform sampler2D WindNoise;
//uniform int NumOfInstances;
uniform float density;
uniform float grassHeight;

uint triple32(uint x) {
    x ^= x >> 17;
    x *= 0xed5ad4bbU;
    x ^= x >> 11;
    x *= 0xac4c1b51U;
    x ^= x >> 15;
    x *= 0x31848babU;
    x ^= x >> 14;
    return x;
}

float hash(uint x){
    return float( triple32(x) ) / float( 0xffffffffU );
}

float hash12(vec2 p){
    vec3 p3  = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

mat2 rot2D(float angle) {
    float rad = (angle * 3.151592)/180.0;
    float s = sin(rad);
    float c = cos(rad);
    return mat2(c, -s, s, c);
}

float getGrassHeightGradient(float height){
    return height / grassHeight;
}

out vec3 localPos;
out vec3 normal;

void main() {
    vec3 pos = Position;

    vec3 cameraPos = VeilCamera.CameraPosition;

    float cameraX = mod(cameraPos.x, 1);
    float cameraZ = mod(cameraPos.z, 1);
    cameraPos.xz = vec2(cameraX, cameraZ);

    vec3 offset = myBuffer.position[gl_InstanceID];

    vec3 WorldPos = offset + floor(VeilCamera.CameraPosition);
    float rand = hash12(WorldPos.xz);

    vec3 tempNormal = Normal;
    float randAngle = rand * 360;
    mat2 randRot = rot2D(randAngle);
    pos.xz *= randRot;
    tempNormal.xz *= randRot;






    localPos = (pos - cameraPos) + offset;


    float windStrength = 0.5;

    #ifdef LEVEL324
    windStrength = 1.5;
    #endif

    vec3 worldPos = localPos + VeilCamera.CameraPosition;
    float grassGradient = getGrassHeightGradient(pos.y);
    float windtexture = texture(WindNoise, (worldPos.xz * 0.03) + GameTime * 100 + rand* 0.1).r - 0.3;
    float heightTexture = clamp((texture(WindNoise, (WorldPos.xz * 0.1)).r * texture(WindNoise, (WorldPos.xz * 0.01)).r), 0.0, 1.0);
    heightTexture = 2.5 * (heightTexture - 0.5) + 0.5;
    localPos.y += heightTexture * grassGradient;
    localPos.xz -= 2 * (grassGradient * grassGradient) * (windtexture * windStrength) * (grassHeight + heightTexture);
    tempNormal.y = 2 * (grassGradient * grassGradient) * (windtexture * windStrength) * (grassHeight + heightTexture);

    normal = tempNormal;

    gl_Position = VeilCamera.ProjMat * VeilCamera.ViewMat * vec4(localPos, 1.0);
}