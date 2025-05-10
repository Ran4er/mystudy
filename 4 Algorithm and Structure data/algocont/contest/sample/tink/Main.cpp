#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>

using namespace std;

const int MOD = 998244353;

// Находит НОД двух чисел
int gcd(int a, int b) {
    while (b) {
        a %= b;
        swap(a, b);
    }
    return a;
}

// Находит НОД всех чисел в векторе
int find_gcd(const vector<int>& nums) {
    int result = nums[0];
    for (size_t i = 1; i < nums.size(); ++i) {
        result = gcd(result, nums[i]);
        if (result == 1) return 1; // Оптимизация: если уже нашли 1, дальше искать не нужно
    }
    return result;
}

// Проверяет условие b[i]/b[i-1] = a[i-1]
bool check_ratio_condition(const vector<int>& b, const vector<int>& a) {
    for (size_t i = 1; i < b.size(); ++i) {
        if (b[i] != b[i-1] * a[i-1]) {
            return false;
        }
    }
    return true;
}

int solve(int n, const vector<int>& a) {

    
    // Общее решение
    long long sum = 0;
    
    // Перебираем все возможные значения для первого элемента
    for (int first = 1; first <= 1000; ++first) {
        // Строим последовательность b
        vector<int> b(n);
        b[0] = first;
        
        // Вычисляем остальные элементы последовательности
        for (int i = 1; i < n; ++i) {
            long long next = static_cast<long long>(b[i-1]) * a[i-1];
            // Проверяем, что следующий элемент не превышает ограничения
            if (next > 1000) {
                b.clear();
                break;
            }
            b[i] = next;
        }
        
        // Если последовательность построена полностью
        if (!b.empty()) {
            // Проверяем, что НОД всех элементов равен 1
            if (find_gcd(b) == 1) {
                // Вычисляем интересность и добавляем к сумме
                long long interest = 1;
                for (int val : b) {
                    interest = (interest * val) % MOD;
                }
                sum = (sum + interest) % MOD;
            }
        }
    }
    
    return static_cast<int>(sum);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;
    cin >> n;
    
    vector<int> a(n - 1);
    for (int i = 0; i < n - 1; ++i) {
        cin >> a[i];
    }
    
    cout << solve(n, a) << endl;
    
    return 0;
}