#include <climits>
#include <cmath>
#include <iostream>
#include <vector>

using namespace std;

int main() {
  int n;
  cin >> n;
  vector<int> stones(n);
  int total_sum = 0;

  for (int i = 0; i < n; ++i) {
    cin >> stones[i];
    total_sum += stones[i];
  }

  int min_diff = INT_MAX;

  for (int mask = 0; mask < (1 << n); ++mask) {
    int sum1 = 0;
    for (int i = 0; i < n; ++i) {
      if (mask & (1 << i)) {
        sum1 += stones[i];
      }
    }

    int sum2 = total_sum - sum1;
    min_diff = min(min_diff, abs(sum1 - sum2));
  }

  cout << min_diff << endl;

  return 0;
}
