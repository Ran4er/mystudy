#include<stdlib.h>
#include<stdio.h>
#include<inttypes.h>
#include<stdbool.h>

struct maybe_int64 {int64_t value; bool valid;};
struct array_int {int64_t* data; size_t size;};
const struct maybe_int64 none_int64 = { 0, false };

struct stack {
  size_t count;
  struct array_int data;
};

struct maybe_int64 array_int_get( struct array_int a, size_t i ) {
    
    if(i > a.size) return none_int64;
    struct maybe_int64 getter = { (a.data)[i], 1 };
    return getter;
}

// Количество элементов в стеке
size_t stack_count( const struct stack* s ){
    return s->count;
};

// Создаем и деинициализируем стек
struct stack stack_create( size_t size ){


};
void stack_destroy( struct stack* s ){

};

// Стек полный
bool stack_is_full( const struct stack * s){
    if(s->count == 0) return false;
    if(s->data.size == 0) return false;
    return true;
};
// Стек пустой
bool stack_is_empty( const struct stack * s){
    if(s->count == 0) return true;
    if(s->data.size == 0) return true;
    return false;
};

// Поместить значение в стек
bool stack_push( struct stack* s, int64_t value );

// Вынуть значение с вершины стека. Может вернуть none_int64
struct maybe_int64 stack_pop( struct stack* s ){


};

void stack_print( const struct stack* s ) {
  for (size_t i = 0; i < stack_count( s ); i = i + 1 ) {
    print_int64( array_int_get( s->data, i).value );
    printf(" ");
  }
}

int main(){

    return 0;
}