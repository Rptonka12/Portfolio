#include <mutex>
#include <stdio.h>
#include <iostream>

template <typename T>
class SharedQueue {
public:
	SharedQueue();
	~SharedQueue();
    SharedQueue(const SharedQueue<T>&) = delete;
    SharedQueue<T>& operator=(const SharedQueue<T>&) = delete;

	//Return true if the queue is empty
	bool IsEmpty() const;
  
	//Enqueue the next item at the tail of the queue.
	void Add(T value);
  
	//Dequeue the next queue element and store it in "item" variable.  The function returns false if the queue is empty and no item can be retrieved.
	bool Remove(T &item);
	void Print();

private:
	struct QueueItem {
		T item;
		QueueItem *next;
	};
  
	//Fill in the the private data members.
	QueueItem* head;
	QueueItem* tail;
	std::mutex* listlock;
};

//Fill in the function definitions
template<typename T>
inline SharedQueue<T>::SharedQueue()
{
	head = nullptr;
	tail = nullptr;
	listlock = new std::mutex();
}

template<typename T>
inline SharedQueue<T>::~SharedQueue()
{
	QueueItem* cursor = head;
	while (cursor != nullptr) {
		QueueItem* temp = cursor;
		cursor = cursor->next;
		temp->next = nullptr;
		delete temp;
	}
}

template<typename T>
inline bool SharedQueue<T>::IsEmpty() const
{
	if (head == nullptr) {
		return true;
	} else return false;
}

template<typename T>
inline void SharedQueue<T>::Add(T value) 
{
	listlock->lock();
	QueueItem* temp = new QueueItem();
	temp->item = value;
	temp->next = nullptr;
	if (IsEmpty()) {
		head = temp;
		tail = temp;
	}
	else if (head == temp) {
		head->next = temp;
		tail = temp;
	}
	else {
		tail->next = temp;
		tail = temp;
	}
	listlock->unlock();
}

template<typename T>
inline bool SharedQueue<T>::Remove(T & item) 
{
	listlock->lock();
	if (IsEmpty()) {
		//std::cout << "List is empty" << std::endl;
		listlock->unlock();
		return false;
	}
	else {
		QueueItem* newHead = head->next;
		item = head->item;
		head->next = nullptr;
		delete head;
		head = newHead;
		listlock->unlock();
		return true;
	}
}

template<typename T>
inline void SharedQueue<T>::Print()
{
	listlock->lock();
	if (IsEmpty()) {
		std::cout << "No values to print, empty list" << std::endl;
	}
	
	QueueItem* cursor = head;
	while (cursor != nullptr) {
		std::cout << "( " << cursor->item << " )" << std::endl;
		cursor = cursor->next;
	}
	listlock->unlock();
}