#include<stdio.h>
#include<stdlib.h>
#include<inttypes.h>

void print_newline() {
    printf("\n");
}

int64_t read_int64() {
    int64_t num;
    scanf("%" SCNd64, &num);
    return num;
}

size_t read_size() {
    size_t size;
    scanf("%zu", &size);
    return size;
}

int64_t* array_int_min(int64_t* array, size_t size) {
    if (size == 0) return NULL;
    if (size == 1) return &array[0];

    int64_t min = 0;
    for (size_t i = 1; i < size; i++) {
        if (array[min] > array[i]) min = i;
    }

    return &array[min];
}

void array_int_fill(int64_t* array, size_t size) {
    for (size_t i = 0; i < size; i++) {
        array[i] = read_int64();
    }
}

void array_int_print(int64_t* array, size_t size) {
    for (size_t i = 0; i < size; i++) {
        printf("%" PRId64 " ", array[i]);
    }
}

int64_t* array_int_read(size_t* size) {
    *size = read_size();

    if (*size == 0) return NULL;

    int64_t* arr = malloc(sizeof(int64_t) * (*size));

    array_int_fill(arr, *size);

    return arr;
}

int64_t** marray_read(size_t* rows, size_t** sizes) {
    *rows = read_size();

    if (*rows == 0) {
        *sizes = NULL;
        return NULL;
    }

    int64_t** marray = malloc(sizeof(int64_t*) * (*rows));
    *sizes = malloc(sizeof(size_t) * (*rows));

    for (size_t i = 0; i < *rows; i++) {
        marray[i] = array_int_read(&(*sizes)[i]);
    }

    return marray;
}

void marray_print(int64_t** marray, size_t* sizes, size_t rows) {
    for (size_t i = 0; i < rows; i++) {
        array_int_print(marray[i], sizes[i]);
        print_newline();
    }
}

void marray_free(int64_t** marray, size_t rows) {
    for (size_t i = 0; i < rows; i++) {
        free(marray[i]);
    }
    free(marray);
}

int64_t* int64_ptr_min(int64_t* x, int64_t* y) {
    if (x == NULL && y == NULL) return NULL;
    if (x == NULL) return y;
    if (y == NULL) return x;

    return (*x < *y) ? x : y;
}

int64_t* marray_int_min(int64_t** marray, size_t* sizes, size_t rows) {
    if (rows == 0) return NULL;

    int64_t* min_ptr = marray[0];
    for (size_t i = 1; i < rows; i++) {
        min_ptr = int64_ptr_min(min_ptr, array_int_min(marray[i], sizes[i]));
    }

    return min_ptr;
}

void marray_normalize(int64_t** marray, size_t sizes[], size_t rows, int64_t m) {
    for (size_t i = 0; i < rows; i++) {
        for (size_t j = 0; j < sizes[i]; j++) {
            marray[i][j] -= m;
        }
    }
}

void perform() {
    size_t rows;
    size_t* sizes;
    int64_t** marray = marray_read(&rows, &sizes);

    int64_t* min_ptr = marray_int_min(marray, sizes, rows);

    if (min_ptr != NULL) {
        marray_normalize(marray, sizes, rows, *min_ptr);
        marray_print(marray, sizes, rows);
    }

    free(sizes);

    marray_free(marray, rows);
}

int main() {
    perform();

    return 0;
}