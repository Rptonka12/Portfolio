//
//  surfaceExtraction.hpp
//  Homework3
//
//  Created by Ben Jones on 1/6/17.
//  Copyright © 2017 Ben Jones. All rights reserved.
//

#pragma once
#include "VoxelModel.h"
#include <iostream>

enum FaceType {
    NX,
    NY,
    NZ,
    PX,
    PY,
    PZ
};

struct Triangle{
    float normal[3];
    float v1[3];
    float v2[3];
    float v3[3];
};

void extractFace(int x, int y, int z, FaceType face, Triangle& t1, Triangle& t2);
void writeSTL(VoxelModel& model, const char* filename);
void writeTriangle(Triangle t, FILE* f);