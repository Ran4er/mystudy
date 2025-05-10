#include <iostream>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int T;
  cin >> T;

  while (T--) {
    long long n, k;
    cin >> n >> k;

    long long m = n / k;
    long long r = n % k;
    long long sum_sq = r * (m + 1) * (m + 1) + (k - r) * m * m;
    long long ans = (n * n - sum_sq) / 2;

    cout << ans << '\n';
  }

  return 0;
}

Алгоритм Карацубо и Нотации O(n) & o(n)