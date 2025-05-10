#include <bits/stdc++.h>
using namespace std;
using ll = long long;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n;
  vector<vector<ll>> w(n, vector<ll>(n));
  ll lo = 0, hi = 0;
  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      cin >> w[i][j];
      if (i != j)
        hi = max(hi, w[i][j]);
    }
  }
  if (n == 1) {
    cout << 0 << "\n";
    return 0;
  }

  auto is_strongly_connected = [&](ll cap) {
    vector<char> vis(n, 0);
    queue<int> q;
    vis[0] = 1;
    q.push(0);
    while (!q.empty()) {
      int u = q.front();
      q.pop();
      for (int v = 0; v < n; v++) {
        if (!vis[v] && w[u][v] <= cap) {
          vis[v] = 1;
          q.push(v);
        }
      }
    }
    for (int i = 0; i < n; i++)
      if (!vis[i])
        return false;

    vis.assign(n, 0);
    vis[0] = 1;
    q.push(0);
    while (!q.empty()) {
      int u = q.front();
      q.pop();
      for (int v = 0; v < n; v++) {
        if (!vis[v] && w[v][u] <= cap) {
          vis[v] = 1;
          q.push(v);
        }
      }
    }
    for (int i = 0; i < n; i++)
      if (!vis[i])
        return false;

    return true;
  };

  ll ans = hi;
  while (lo <= hi) {
    ll mid = lo + (hi - lo) / 2;
    if (is_strongly_connected(mid)) {
      ans = mid;
      hi = mid - 1;
    } else {
      lo = mid + 1;
    }
  }

  cout << ans << "\n";
  return 0;
}
