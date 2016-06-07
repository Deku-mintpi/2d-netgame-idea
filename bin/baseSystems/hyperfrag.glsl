// Poincare disk fragment shader.

// precision mediump float;

#version 150

varying vec2 vPos;

#define MAX_ARCS 16
uniform int numArcs;
uniform vec3 arcs[MAX_ARCS];
uniform vec4 color;
uniform float aspect;

// Known to work with some slight aliasing issues and edge issues.
void main() {
    vec2 pos = vec2(vPos.x * aspect, vPos.y);
    vec4 outColor = color;
    for(int i = 0; i < MAX_ARCS; i++) {
        if(i < numArcs) {
            float rightSide = arcs[i].z * (1.0 + pos.x*pos.x + pos.y*pos.y);
            float leftSide = arcs[i].x * pos.x + arcs[i].y * pos.y;
            if(leftSide <= rightSide) {
                outColor = vec4(0.0, 0.0, 0.0, 0.0);
            }
        }
    }
    if(length(pos) > 1.0) {
        outColor = vec4(0.0, 0.0, 0.0, 0.0);
    }
    gl_FragColor = outColor;
}