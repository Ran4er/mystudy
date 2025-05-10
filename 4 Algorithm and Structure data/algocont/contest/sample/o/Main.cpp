#include <bits/stdc++.h>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int N, M;
  cin >> N >> M;

  vector<vector<int>> adj(N + 1);
  for (int i = 0; i < M; i++) {
    int u, v;
    cin >> u >> v;
    adj[u].push_back(v);
    adj[v].push_back(u);
  }

  vector<int> color(N + 1, -1);

  for (int start = 1; start <= N; start++) {
    if (color[start] != -1)
      continue;

    queue<int> q;
    color[start] = 0;
    q.push(start);

    while (!q.empty()) {
      int u = q.front();
      q.pop();
      for (int v : adj[u]) {
        if (color[v] == -1) {
          color[v] = color[u] ^ 1;
          q.push(v);
        } else if (color[v] == color[u]) {
          cout << "NO\n";
          return 0;
        }
      }
    }
  }

  cout << "YES\n";
  return 0;
}
