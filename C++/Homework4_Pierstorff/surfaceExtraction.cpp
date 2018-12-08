//
//  surfaceExtraction.cpp
//  Homework3
//
//  Created by Ben Jones on 1/6/17.
//  Copyright © 2017 Ben Jones. All rights reserved.
//

#define _CRT_SECURE_NO_WARNINGS
#include "surfaceExtraction.hpp"
#include "VoxelModel.h"
#include <stdio.h>
#include <iostream>

void writeSTL(VoxelModel& model, const char* filename) {
	FILE* f = fopen(filename, "wb+");
	
	//header
	uint8_t header[80];
	for (int i = 0; i < 80; i++) { header[i] = 0; }
	fwrite(header, 80, 1, f);

	//placeholder number of triangles
	uint32_t numTriangles = 0;
	fwrite(&numTriangles, sizeof(numTriangles), 1, f);

	//identifying faces and writing triangles
	int size = model.nx * model.ny * model.nz;
	for (int i = 0; i < size; i++) { 
		int voxX;
		int voxY;
		int voxZ;
		getXYZ(model, i, voxX, voxY, voxZ);
		
		if (getBit(model, voxX, voxY, voxZ) == 1) {
			//X triangles
			if ((voxX == 0) || (!getBit(model, (voxX - 1), voxY, voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, NX, t1, t2);
				writeTriangle(t1, f);
				writeTriangle(t2, f);
				numTriangles += 2; 
			}
			if ((voxX == (model.nx-1)) || (!getBit(model, (voxX + 1), voxY, voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, PX, t1, t2);
				writeTriangle(t1, f);
				writeTriangle(t2, f);
				numTriangles += 2;
			}
			//Y triangles
			if ((voxY == 0) || (!getBit(model, voxX, (voxY - 1), voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, NY, t1, t2);
				writeTriangle(t1, f);
				writeTriangle(t2, f);
				numTriangles += 2;
			}
			if ((voxY == (model.ny-1)) || (!getBit(model, voxX, (voxY + 1), voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, PY, t1, t2);
				writeTriangle(t1, f);
				writeTriangle(t2, f);
				numTriangles += 2;
			}
			//Z triangles
			if ((voxZ == 0) || (!getBit(model, voxX, voxY, (voxZ - 1)))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, NZ, t1, t2);
				writeTriangle(t1, f);
				writeTriangle(t2, f);
				numTriangles += 2;
			}
			if ((voxZ == (model.nz-1)) || (!getBit(model, voxX, voxY, (voxZ + 1)))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, PZ, t1, t2);
				writeTriangle(t1, f);
				writeTriangle(t2, f);
				numTriangles += 2;
			}
		}
	}

	//seek to add number of triangles 
	fseek(f, 80, SEEK_SET);

	//writing number of triangles
	if (fwrite(&numTriangles, sizeof(numTriangles), 1, f) != 1) {
		std::cout << "Did not add number of triangles successfully" << std::endl;
	}
	else {
		std::cout << "Added " << numTriangles << " triangles successfully" << std::endl;
	}

	//closing file
	fclose(f);
}

void writeTriangle(Triangle t, FILE* f) {
	//normal 
	for (int i = 0; i < 3; i++) {
		fwrite(&t.normal[i], sizeof(float), 1, f);
	}
	//vertex 1
	for (int i = 0; i < 3; i++) {
		fwrite(&t.v1[i], sizeof(float), 1, f);
	}
	//vertex 2
	for (int i = 0; i < 3; i++) {
		fwrite(&t.v2[i], sizeof(float), 1, f);
	}
	//vertex 3
	for (int i = 0; i < 3; i++) {
		fwrite(&t.v3[i], sizeof(float), 1, f);
	}
	//attribute byte count
	uint16_t filler = 0;
	fwrite(&filler, sizeof(filler), 1, f);
}

inline void fillPlane(int a1, int a2, int b1, int b2, int c, int cInd, Triangle& t1, Triangle& t2){
    t1.v1[cInd] = c;
    t2.v1[cInd] = c;
    t1.v2[cInd] = c;
    t2.v2[cInd] = c;
    t1.v3[cInd] = c;
    t2.v3[cInd] = c;
    int aInd = (cInd +1) % 3;
    int bInd = (cInd +2) % 3;
    
    t1.v1[aInd] = a1;
    t1.v2[aInd] = a2;
    t1.v3[aInd] = a2;
    
    t2.v1[aInd] = a1;
    t2.v2[aInd] = a2;
    t2.v3[aInd] = a1;
    
    t1.v1[bInd] = b1;
    t1.v2[bInd] = b1;
    t1.v3[bInd] = b2;
    
    t2.v1[bInd] = b1;
    t2.v2[bInd] = b2;
    t2.v3[bInd] = b2;
}

void extractFace(int x, int y, int z, FaceType face, Triangle& t1, Triangle& t2){
    for(int i= 0; i < 3; i++){
        t1.normal[i] = 0;
        t2.normal[i] = 0;
    }
    switch(face){
        case NX:
            t1.normal[0] = -1;
            t2.normal[0] = -1;
            fillPlane(y + 1, y, z, z+1, x, 0, t1, t2);
            break;
        case NY:
            t1.normal[1] = -1;
            t2.normal[1] = -1;
            fillPlane(z + 1, z, x, x+1, y, 1, t1, t2);
            break;
        case NZ:
            t1.normal[2] = -1;
            t2.normal[2] = -1;
            fillPlane(x + 1, x, y, y + 1, z, 2, t1, t2);
            break;
        case PX:
            t1.normal[0] = 1;
            t2.normal[0] = 1;
            fillPlane(y, y + 1, z, z +1, x + 1, 0, t1, t2);
            break;
        case PY:
            t1.normal[1] = 1;
            t2.normal[1] = 1;
            fillPlane(z, z + 1, x, x + 1, y + 1, 1, t1, t2);
            break;
        case PZ:
            t1.normal[2] = 1;
            t2.normal[2] = 1;
            fillPlane(x, x +1, y, y + 1, z + 1, 2, t1, t2);
            break;
    }
}