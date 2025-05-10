#include <cstddef>
#include <bits/stdc++.h>
#include <vector>
using namespace std;

pair<u_int64_t, u_int64_t> solve(const vector<u_int64_t>& a) {
  u_int64_t n = a.size();
  u_int64_t max_len = 0;
  u_int64_t start = 0;
  u_int64_t result_start = 0;
  u_int64_t result_end = 0;

  for (size_t end = 0; end < n; ++end) {
    if (end >= 2 && a[end] == a[end - 1] && a[end] == a[end - 2]) {
      start = end - 1;
    }
    u_int64_t current_len = end - start + 1;

    if (current_len > max_len) {
      max_len = current_len;
      result_start = start + 1;
      result_end = end + 1;
    }
  }

  return {result_start, result_end};
}

int main() {
  u_int64_t n;
  cin >> n;
  vector<u_int64_t> a(n);

  for (size_t i = 0; i < n; ++i) {
    cin >> a[i];
  }

  pair<u_int64_t, u_int64_t> result = solve(a);

  cout << result.first << " " << result.second << endl;

  a.clear();
  a.shrink_to_fit();

  return 0;
}