#include<stdlib.h>
#include<stdio.h>
#include<inttypes.h>

struct list {
    int64_t value;
    struct list* next;
};

struct list* node_create( int64_t value ) {
    struct list* node_ptr = malloc(sizeof(struct list));
    node_ptr->value = value;
    node_ptr->next=node_ptr;
    return node_ptr;
}

int main(){

    int64_t a = 42;
    struct list* node_create(a);
    
    return 0;
}