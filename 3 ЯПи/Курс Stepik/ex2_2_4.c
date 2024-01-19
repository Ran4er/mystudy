#include<stdlib.h>
#include<stdio.h>
#include<inttypes.h>

void print_newline(){
    printf("\n");
}

int64_t read_int64(){
    int64_t num;
    scanf("%" SCNd64, &num);
    return num;
}

size_t read_size(){
    size_t size;
    scanf("%zu", &size);
    return size;
}

void array_int_fill( int64_t* array, size_t size ) {

    for(size_t i = 0; i < size; i++){
        array[i] = read_int64();
    }
}

int64_t* array_int_read( size_t* size ) {
  
    *size = read_size();

    if(*size == 0) return 0;

    int64_t* arr = malloc(sizeof(int64_t) * (*size));

    array_int_fill(arr, *size);

    return arr;
}


int64_t** marray_read(size_t* rows, size_t** sizes){

    rows = read_size();
    

}

void marray_print(int64_t** marray, size_t* sizes, size_t rows){

    for( size_t i = 0; i < rows; i = i + 1 ) {
        array_int_print( marray[i], sizes[i] );
        print_newline();
    }

}


int main(){



    return 0;

}