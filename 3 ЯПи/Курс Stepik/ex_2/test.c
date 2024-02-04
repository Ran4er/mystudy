#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

#ifndef _cplusplus
    typedef unsigned char bool;
    static const bool true = 1;
    static const bool false = 0;
#endif

/* Вы можете пользоваться этими функциями из предыдущих заданий */
size_t read_size() { size_t i; scanf("%zu", &i); return i; }

int64_t read_int64() {
    int64_t num;
    scanf("%" SCNd64, &num);
    return num;
}

void array_int_fill(int64_t* array, size_t sz) {
    for (size_t i = 0; i < sz; i++) {
        array[i] = read_int64();
    }
}

struct maybe_int64 {
    int64_t value;
    bool valid;
};

struct array_int {
    int64_t* data;
    size_t size;
};

const struct maybe_int64 none_int64 = { 0 };

struct array_int array_int_read() {
    const size_t size = read_size();
    if (size > 0) {
        int64_t* array = malloc(sizeof(int64_t) * size);
        array_int_fill(array, size);
        return (struct array_int){array, size};
    } else {
        return (struct array_int){NULL, 0};
    }
}

struct maybe_int64 array_int_get(struct array_int a, size_t i) {
    if (i < a.size) {
        return (struct maybe_int64){a.data[i], true};
    } else {
        return none_int64;
    }
}

bool array_int_set(struct array_int a, size_t i, int64_t value) {
    if (i < a.size) {
        a.data[i] = value;
        return true;
    } else {
        return false;
    }
}

void array_int_print(struct array_int array) {
    for (size_t i = 0; i < array.size; i++) {
        printf("%" PRId64 " ", array_int_get(array, i).value);
    }
}

struct maybe_int64 array_int_min(struct array_int array) {
    if (array.size == 0) {
        return none_int64;
    }
    int64_t min = array.data[0];
    for (size_t i = 1; i < array.size; i++) {
        if (array.data[i] < min) {
            min = array.data[i];
        }
    }
    return (struct maybe_int64){min, true};
}

void array_int_free(struct array_int a) {
    if (a.data != NULL) {
        free(a.data);
    }
}

void array_int_normalize(struct array_int array, int64_t m) {
    for (size_t i = 0; i < array.size; i++) {
        array.data[i] -= m;
    }
}

struct maybe_array_int {
    struct array_int value;
    bool valid;
};

struct array_array_int {
    struct array_int* data;
    size_t size;
};

struct array_array_int array_array_int_read() {
    const size_t rows = read_size();
    struct array_array_int result;
    result.size = rows;
    result.data = malloc(sizeof(struct array_int) * rows);
    for (size_t i = 0; i < rows; i++) {
        result.data[i] = array_int_read();
    }
    return result;
}

void array_array_int_print(struct array_array_int array) {
    for (size_t i = 0; i < array.size; i++) {
        array_int_print(array.data[i]);
        printf("\n");
    }
}

struct maybe_int64 array_array_int_get(struct array_array_int a, size_t i, size_t j) {
    if (i < a.size && j < a.data[i].size) {
        return array_int_get(a.data[i], j);
    } else {
        return none_int64;
    }
}

bool array_array_int_set(struct array_array_int a, size_t i, size_t j, int64_t value) {
    if (i < a.size && j < a.data[i].size) {
        return array_int_set(a.data[i], j, value);
    } else {
        return false;
    }
}

struct maybe_int64 array_array_int_min(struct array_array_int array) {
    if (array.size == 0) {
        return none_int64;
    }
    int64_t min = array.data[0].data[0];
    for (size_t i = 0; i < array.size; i++) {
        for (size_t j = 0; j < array.data[i].size; j++) {
            if (array.data[i].data[j] < min) {
                min = array.data[i].data[j];
            }
        }
    }
    return (struct maybe_int64){min, true};
}

void array_array_int_normalize(struct array_array_int array, int64_t m) {
    for (size_t i = 0; i < array.size; i++) {
        array_int_normalize(array.data[i], m);
    }
}

void array_array_int_free(struct array_array_int array) {
    for (size_t i = 0; i < array.size; i++) {
        array_int_free(array.data[i]);
    }
    free(array.data);
}

void perform() {
    struct array_array_int array = array_array_int_read();
    struct maybe_int64 m = array_array_int_min(array);
    if (m.valid) {
        array_array_int_normalize(array, m.value);
        array_array_int_print(array);
    }
    array_array_int_free(array);
}

int main() {
    perform();
    return 0;
}
