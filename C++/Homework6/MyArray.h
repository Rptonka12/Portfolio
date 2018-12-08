#pragma once
#include <stdio.h>
#include <iostream>

template <typename T>
class MyArray {
public:
	MyArray(int elements = 20); //constructor
	~MyArray(); //destructor
	MyArray(const MyArray<T>&); //copy constructor
	MyArray<T>& operator=(const MyArray<T>&); //operator = override
	T Get(int index) const;
	void Set(int index, T value);
	int Size() const;
	void PushBack(T value);
	T Back();
	bool PopBack(T& value);
private:
	T * myarray;
	int size;
	int capacity;
	void Resize(int newSize);
};

template<typename T>
MyArray<T>::MyArray(int elements)
{
	if (elements > 20) {
		myarray = new T[elements];
		capacity = elements;
	}
	else {
		myarray = new T[20];
		capacity = 20;
	}
	size = 0;
}

template<typename T>
MyArray<T>::~MyArray()
{
	delete[] myarray;
	myarray = NULL;
	capacity = 0;
	size = 0;
}

template<typename T>
MyArray<T>::MyArray(const MyArray<T>& source)
{
	if (source.Size() > 20) {
		myarray = new T[source.Size()];
		capacity = source.Size();
	}
	else {
		myarray = new T[20];
		capacity = 20;
	}
	for (int i = 0; i < source.Size(); i++) {
		this->Set(i, source.Get(i));
	}
	size = source.Size();
}

template<typename T>
MyArray<T>& MyArray<T>::operator=(const MyArray<T>& source)
{
	if (this == &source) return *this;

	if (source.Size() > 20) {
		myarray = new T[source.Size()];
		capacity = source.Size();
	}
	else {
		myarray = new T[20];
		capacity = 20;
	}

	for (int i = 0; i < source.Size(); i++) {
		this->Set(i, source.Get(i));
	}
	size = source.Size();
	return *this;
}

template<typename T>
T MyArray<T>::Get(int index) const
{
	return myarray[index];
}

template<typename T>
void MyArray<T>::Set(int index, T value)
{
	if (index > size) {
		std::cout << "Index greater than array bounds" << std::endl;
	}
	else if (index < 0) {
		std::cout << "Index less than 0" << std::endl;
	}
	else {
		myarray[index] = value;
	}
}

template<typename T>
int MyArray<T>::Size() const
{
	return this->size;
}

template<typename T>
inline void MyArray<T>::Resize(int newSize)
{
	T* newArray = new T[newSize];

	if (newSize >= size) { //old values will fit (size/number of elements doesn't change)
		for (int i = 0; i < size; i++) {
			newArray[i] = myarray[i];
		}
	}
	else {
		for (int i = 0; i < newSize; i++) { //some old values will be disregarded, because array gets smaller
			newArray[i] = myarray[i];
		}
		size = newSize;
	}

	capacity = newSize;
	delete[] myarray;
	myarray = newArray;
}

template<typename T>
inline void MyArray<T>::PushBack(T value)
{
	if (size == capacity) {
		Resize(capacity * 2);
	}
	myarray[size++] = value;
}

template<typename T>
inline T MyArray<T>::Back()
{
	return myarray[size - 1];
}

template<typename T>
inline bool MyArray<T>::PopBack(T & value)
{
	value = myarray[size - 1];
	size--;
	this->Set(size , NULL);
	return true;
}