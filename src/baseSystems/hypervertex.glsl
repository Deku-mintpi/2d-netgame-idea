#version 150

precision mediump float;

attribute vec3 pos;
varying vec2 vPos;

void main() {
    vPos = pos.xy;
    //vPos = (mul(pos, a) + b) / (mul(pos, c) + d);
    gl_Position = vec4(vPos, 0.0, 1.0);
}