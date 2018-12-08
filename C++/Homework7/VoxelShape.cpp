#define _CRT_SECURE_NO_WARNINGS
#include "VoxelShape.h"
#include "SharedQueue.h"
#include "Timer.h"
#include <iostream>
#include <mutex>

VoxelShape::VoxelShape(int x, int y, int z)
{
	mx = x;
	my = y;
	mz = z;
	int numBits = x * y * z; 
	storedBits = new BitVector(numBits);
	shapeSize = storedBits->size();
}

VoxelShape::~VoxelShape()
{
	delete storedBits;
	storedBits = nullptr;
}

void VoxelShape::fill() { storedBits->fillModel(); }
void VoxelShape::clear() { storedBits->clearModel(); }

//Bit Setting Functions
bool VoxelShape::getVoxel(int x, int y, int z)
{
	int index = getIndex(x, y, z);
	return storedBits->getBit(index);
}

void VoxelShape::setVoxel(int x, int y, int z)
{
	int index = getIndex(x, y, z);
	return storedBits->setBit(index, 1);
}

void VoxelShape::clearVoxel(int x, int y, int z)
{
	int index = getIndex(x, y, z);
	return storedBits->setBit(index, 0);
}

void VoxelShape::toggleVoxel(int x, int y, int z)
{
	int index = getIndex(x, y, z);
	if (storedBits->getBit(index)) {
		storedBits->setBit(index, 0);
	}
	else {
		storedBits->setBit(index, 1);
	}
}

//Sphere Functions
bool VoxelShape::insideSphere(int index, float cx, float cy, float cz, float radius)
{
	int x, y, z;
	getXYZ(index, x, y, z);
	float fx = float(x); 
	float fy = float(y);
	float fz = float(z);
	float distance = pow((fx - cx), 2) + pow((fy - cy), 2) + pow((fz - cz), 2);
	float rad2 = pow(radius, 2);

	if (distance < rad2) { return true; }
	else return false;
}

void VoxelShape::addSphere(float cx, float cy, float cz, float radius)
{
	for (int i = 0; i < storedBits->size(); i++) {
		int bx, by, bz;
		getXYZ(i, bx, by, bz);
		if (insideSphere(i, cx, cy, cz, radius)) {
			setVoxel(bx, by, bz);
		}
	}
}

void VoxelShape::subtractSphere(float cx, float cy, float cz, float radius)
{
	for (int i = 0; i < storedBits->size(); i++) {
		int bx, by, bz;
		getXYZ(i, bx, by, bz);
		if (insideSphere(i, cx, cy, cz, radius)) {
			clearVoxel(bx, by, bz);
		}
	}
}

void VoxelShape::toggleSphere(float cx, float cy, float cz, float radius)
{
	for (int i = 0; i < storedBits->size(); i++) {
		int bx, by, bz;
		getXYZ(i, bx, by, bz);
		if (insideSphere(i, cx, cy, cz, radius)) {
			toggleVoxel(bx, by, bz);
		}
	}
}

int VoxelShape::getIndex(int x, int y, int z)
{
	return ((x * (mx * my)) + (y * my) + z);
}

void VoxelShape::getXYZ(int index, int& x, int& y, int& z)
{
	int tempXY = mx * my;
	x = index / tempXY;
	y = (index - (x * tempXY)) / my;
	z = (index - (y * my)) % my;
}

//File Exporting
enum FaceType { NX, NY, NZ, PX, PY, PZ };

struct Triangle {
	float normal[3];
	float v1[3];
	float v2[3];
	float v3[3];
};

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

inline void fillPlane(int a1, int a2, int b1, int b2, int c, int cInd, Triangle& t1, Triangle& t2) {
	t1.v1[cInd] = c;
	t2.v1[cInd] = c;
	t1.v2[cInd] = c;
	t2.v2[cInd] = c;
	t1.v3[cInd] = c;
	t2.v3[cInd] = c;
	int aInd = (cInd + 1) % 3;
	int bInd = (cInd + 2) % 3;

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

void extractFace(int x, int y, int z, FaceType face, Triangle& t1, Triangle& t2) {
	for (int i = 0; i < 3; i++) {
		t1.normal[i] = 0;
		t2.normal[i] = 0;
	}
	switch (face) {
	case NX:
		t1.normal[0] = -1;
		t2.normal[0] = -1;
		fillPlane(y + 1, y, z, z + 1, x, 0, t1, t2);
		break;
	case NY:
		t1.normal[1] = -1;
		t2.normal[1] = -1;
		fillPlane(z + 1, z, x, x + 1, y, 1, t1, t2);
		break;
	case NZ:
		t1.normal[2] = -1;
		t2.normal[2] = -1;
		fillPlane(x + 1, x, y, y + 1, z, 2, t1, t2);
		break;
	case PX:
		t1.normal[0] = 1;
		t2.normal[0] = 1;
		fillPlane(y, y + 1, z, z + 1, x + 1, 0, t1, t2);
		break;
	case PY:
		t1.normal[1] = 1;
		t2.normal[1] = 1;
		fillPlane(z, z + 1, x, x + 1, y + 1, 1, t1, t2);
		break;
	case PZ:
		t1.normal[2] = 1;
		t2.normal[2] = 1;
		fillPlane(x, x + 1, y, y + 1, z + 1, 2, t1, t2);
		break;
	}
}

//Part 1 of Assignment 7
void Zextract(int voxX, int voxY, MyArray<Triangle>* triangles, VoxelShape & model) {
	for (int voxZ = 0; voxZ < model.mz; voxZ++) {
		if (model.getVoxel(voxX, voxY, voxZ) == 1) {
 			//X Triangles
			if ((voxX == 0) || (!model.getVoxel((voxX - 1), voxY, voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, NX, t1, t2);
				triangles->PushBack(t1);
				triangles->PushBack(t2);
			}
			if ((voxX == (model.mx - 1)) || (!model.getVoxel((voxX + 1), voxY, voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, PX, t1, t2);
				triangles->PushBack(t1);
				triangles->PushBack(t2);
			}
			//Y triangles
			if ((voxY == 0) || (!model.getVoxel(voxX, (voxY - 1), voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, NY, t1, t2);
				triangles->PushBack(t1);
				triangles->PushBack(t2);
			}
			if ((voxY == (model.my - 1)) || (!model.getVoxel(voxX, (voxY + 1), voxZ))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, PY, t1, t2);
				triangles->PushBack(t1);
				triangles->PushBack(t2);
			}
			//Z triangles
			if ((voxZ == 0) || (!model.getVoxel(voxX, voxY, (voxZ - 1)))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, NZ, t1, t2);
				triangles->PushBack(t1);
				triangles->PushBack(t2);
			}
			if ((voxZ == (model.mz - 1)) || (!model.getVoxel(voxX, voxY, (voxZ + 1)))) {
				Triangle t1;
				Triangle t2;
				extractFace(voxX, voxY, voxZ, PZ, t1, t2);
				triangles->PushBack(t1);
				triangles->PushBack(t2);
			}
		}
	}
	//std::cout << triangles->Size() << std::endl;
}

struct Range {
	int xCol;
	int yCol;
};

//Part 3 of Assignment 7
void ThreadWorker(MyArray<Triangle>* triangles, SharedQueue<Range>* queue, std::mutex* lock, VoxelShape& model) {
	Range r;
	MyArray<Triangle> tempResults;

	while (true) {
		//read work from queue
		while (queue->Remove(r) == false) { std::this_thread::yield(); }

		//quit message
		if (r.xCol == -1 && r.yCol == -1) { break; }

		Zextract(r.xCol, r.yCol, &tempResults, std::ref(model));
	}

	//Part 7 of Assignment 7
	lock->lock();
	for (int i = 0; i < tempResults.Size(); i++) {
		triangles->PushBack(tempResults.Get(i));
	}
	lock->unlock();
}

void exportModel(const char * filename, VoxelShape& model){
	FILE* f = fopen(filename, "wb+");
	Timer t;
	t.StartTimer();

	SharedQueue<Range> workQueue;
	MyArray<Triangle> triangles;
	std::mutex* arraylock = new std::mutex();

	//setting up threads
	int numThreads = std::thread::hardware_concurrency();
	std::thread **threads;
	threads = new std::thread*[numThreads];

	for (int i = 0; i < numThreads; i++) {
		threads[i] = new std::thread(ThreadWorker, &triangles, &workQueue, arraylock, std::ref(model));
	}

	//adding work to queue
	for (int voxX = 0; voxX < model.mx; voxX++) {
		for (int voxY = 0; voxY < model.my; voxY++) {
			workQueue.Add({ voxX, voxY });
		}
	}
	for (int i = 0; i < numThreads; i++) {
		workQueue.Add({ -1, -1 });
	}

	//joining threads
	for (int i = 0; i < numThreads; i++) {
		threads[i]->join();
		delete threads[i];
	}
	delete[] threads;

	//Printing time
	std::cout << "Extracting triangles with " << numThreads << " threads took " << t.EndTimer() << " seconds." << std::endl;

	//header
	uint8_t header[80];
	for (int i = 0; i < 80; i++) { header[i] = 0; }
	fwrite(header, 80, 1, f);

	//number of triangles
	uint32_t numTriangles = triangles.Size();
	if (fwrite(&numTriangles, sizeof(numTriangles), 1, f) != 1) {
		std::cout << "Did not add number of triangles successfully" << std::endl;
	}
	else {
		std::cout << "Added " << numTriangles << " triangles successfully" << std::endl;
	}

	//identifying faces and writing triangles
	for (int i = 0; i < numTriangles; i++) {
		writeTriangle(triangles.Get(i), f);
	}

	//closing file
	fclose(f);
}