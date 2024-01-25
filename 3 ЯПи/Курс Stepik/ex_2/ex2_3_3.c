#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

#ifndef _cplusplus

    typedef unsigned char bool;
    static const bool true;
    static const bool false;

#endif

/* Вы можете пользоваться этими функциями из предыдущих заданий */
size_t read_size() { size_t i; scanf("%zu", &i); return i; }

void array_int_fill( int64_t* array, size_t sz );

struct array_int array_int_read();
struct maybe_int64 array_int_get( struct array_int a, size_t i );
bool array_int_set( struct array_int a, size_t i, int64_t value );
void array_int_print( struct array_int array );
struct maybe_int64 array_int_min( struct array_int array );
void array_int_free( struct array_int a );

void array_int_normalize( struct array_int array, int64_t m ) {
  for (size_t i = 0; i < array.size; i = i + 1) {
    array.data[i] = array.data[i] - m;
  }
}

/*  ---- maybe int[] ---- */

struct maybe_array_int {
  struct array_int value;
  bool valid;
};

struct maybe_array_int some_array_int(struct array_int array) {
  return (struct maybe_array_int) { array, true };
}
const struct maybe_array_int none_array_int = { {NULL, 0}, false };


/*  ---- int[][] ---- */

struct array_array_int {
  struct array_int* data;
  size_t size;
};

/*  --- строки ---  */

struct maybe_array_int array_array_int_get_row( struct array_array_int a, size_t i ) {
  if ( 0 <= i && i < a.size ) { return some_array_int( a.data[i] ); }
  else { return none_array_int; }
}

bool array_array_int_set_row( struct array_array_int a, size_t i, struct array_int value ) {
  if (0 <= i && i < a.size) {
    a.data[i] = value;
    return true;
  }
  else { return false; }
}

/*  --- get/set ---  */

struct maybe_int64 array_array_int_get( struct array_array_int a, size_t i, size_t j ) {

    if(i > a.size) return none_array_int;
    if(j > a.data.size) return none_array_int;

    return ((a.data)[i])[j];

}

bool array_array_int_set( struct array_array_int a, size_t i, size_t j, int64_t value ) {

    if(i > a.size) return false;
    if(j > a.data.size) return false;

    (a.data)[i].data[j] = value;
    return true;

}

/*  --- read/print ---  */

struct array_array_int array_array_int_read() {

    size_t rows = 

}


void array_array_int_print( struct array_array_int array) {

    for(size_t i = 0; i < array.size; i++){
        for(size_t j = 0; j < array.data[i].size; j++){

            printf("%" PRId64, array.data[i].data[j]);

        }
    }

}


/*  --- min/normalize ---  */

// найти минимальный элемент в массиве массивов
struct maybe_int64 array_array_int_min( struct array_array_int array ) {

    struct maybe_int64 mini = { array.data[0].data[0], 1};
    for(size_t i = 0; i < array.size; i++){
        for(size_t j = 1; j < array.data[i].size; j++){
            if(mini.value > array.data[i].data[j]) mini.value = array.data[i].data[j];
        }
    }

    return mini;

}

// вычесть из всех элементов массива массивов число m
void array_array_int_normalize( struct array_array_int array, int64_t m) {
  for (size_t i = 0; i < array.size; i = i + 1) {
    const struct maybe_array_int cur_row = array_array_int_get_row( array, i );
    if (cur_row.valid) {
         array_int_normalize( cur_row.value, m );
    }
  }
}

void array_array_int_free( struct array_array_int array ) {

    for(size_t i = 0; i < array.size; i++){

        free(array.data[i]);

    }

    free(array.data);

}

int main(){

    return 0;
}