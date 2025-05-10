#include <iostream>
#include <queue>
#include <string>
#include <vector>
using namespace std;

struct Cell {
  int time;
  int x, y;

  Cell(int time, int x, int y) : time(time), x(x), y(y) {
  }

  bool operator>(const Cell& other) const {
    return time > other.time;
  }
};

int main() {
  int n, m, startX, startY, endX, endY;
  cin >> n >> m >> startX >> startY >> endX >> endY;

  startX--;
  startY--;
  endX--;
  endY--;

  vector<string> map(n);
  for (int i = 0; i < n; i++) {
    cin >> map[i];
  }

  vector<vector<int>> minTime(n, vector<int>(m, -1));

  vector<vector<pair<int, int>>> parent(n, vector<pair<int, int>>(m, {-1, -1}));

  const int dx[] = {-1, 0, 1, 0};
  const int dy[] = {0, 1, 0, -1};
  const char direction[] = {'N', 'E', 'S', 'W'};

  priority_queue<Cell, vector<Cell>, greater<Cell>> pq;
  pq.push(Cell(0, startX, startY));
  minTime[startX][startY] = 0;

  while (!pq.empty()) {
    Cell current = pq.top();
    pq.pop();

    int x = current.x;
    int y = current.y;
    int time = current.time;

    if (time > minTime[x][y])
      continue;

    if (x == endX && y == endY)
      break;

    for (int i = 0; i < 4; i++) {
      int newX = x + dx[i];
      int newY = y + dy[i];

      if (newX >= 0 && newX < n && newY >= 0 && newY < m) {
        if (map[newX][newY] != '#') {
          int newTime = time + (map[newX][newY] == 'W' ? 2 : 1);

          if (minTime[newX][newY] == -1 || newTime < minTime[newX][newY]) {
            minTime[newX][newY] = newTime;
            parent[newX][newY] = {x, y};
            pq.push(Cell(newTime, newX, newY));
          }
        }
      }
    }
  }

  if (minTime[endX][endY] == -1) {
    cout << -1 << endl;
    return 0;
  }

  string path = "";
  int x = endX, y = endY;

  while (x != startX || y != startY) {
    int prevX = parent[x][y].first;
    int prevY = parent[x][y].second;

    for (int i = 0; i < 4; i++) {
      if (prevX + dx[i] == x && prevY + dy[i] == y) {
        path = direction[i] + path;
        break;
      }
    }

    x = prevX;
    y = prevY;
  }

  cout << minTime[endX][endY] << endl;
  cout << path << endl;

  return 0;
}