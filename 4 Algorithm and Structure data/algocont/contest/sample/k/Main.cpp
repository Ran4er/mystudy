#include <iostream>
#include <map>
#include <set>
#include <vector>

using namespace std;

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  long long n;
  int m;
  cin >> n >> m;

  map<int, int> free_blocks;
  free_blocks[1] = n;

  map<int, set<int>> size_to_blocks;
  size_to_blocks[n].insert(1);

  map<int, pair<int, int>> allocations;

  vector<int> results;

  for (int i = 1; i <= m; i++) {
    int request;
    cin >> request;

    if (request > 0) {
      int size_needed = request;

      auto it = size_to_blocks.lower_bound(size_needed);
      if (it == size_to_blocks.end()) {
        results.push_back(-1);
        continue;
      }

      int allocated_pos = -1;
      for (int start : it->second) {
        if (start == 1 || free_blocks.find(start - 1) == free_blocks.end()) {
          allocated_pos = start;
          break;
        }
      }

      if (allocated_pos == -1) {
        results.push_back(-1);
        continue;
      }

      int block_size = it->first;
      int block_start = allocated_pos;
      int block_end = free_blocks[block_start];

      size_to_blocks[block_size].erase(block_start);
      if (size_to_blocks[block_size].empty()) {
        size_to_blocks.erase(block_size);
      }
      free_blocks.erase(block_start);

      if (block_size > size_needed) {
        int new_start = block_start + size_needed;
        int new_size = block_size - size_needed;
        free_blocks[new_start] = block_end;
        size_to_blocks[new_size].insert(new_start);
      }

      allocations[i] = {block_start, size_needed};
      results.push_back(block_start);
    } else {
      int free_request = -request;

      if (allocations.find(free_request) == allocations.end()) {
        continue;
      }

      int start = allocations[free_request].first;
      int size = allocations[free_request].second;
      int end = start + size - 1;

      allocations.erase(free_request);

      auto next_block = free_blocks.find(end + 1);
      if (next_block != free_blocks.end()) {
        int next_end = next_block->second;
        int next_size = next_end - (end + 1) + 1;

        size_to_blocks[next_size].erase(end + 1);
        if (size_to_blocks[next_size].empty()) {
          size_to_blocks.erase(next_size);
        }
        free_blocks.erase(next_block);

        end = next_end;
        size = end - start + 1;
      }

      auto prev_it = free_blocks.upper_bound(start);
      if (prev_it != free_blocks.begin()) {
        prev_it--;
        int prev_start = prev_it->first;
        int prev_end = prev_it->second;

        if (prev_end + 1 == start) {
          int prev_size = prev_end - prev_start + 1;

          size_to_blocks[prev_size].erase(prev_start);
          if (size_to_blocks[prev_size].empty()) {
            size_to_blocks.erase(prev_size);
          }
          free_blocks.erase(prev_it);

          start = prev_start;
          size = end - start + 1;
        }
      }

      free_blocks[start] = end;
      size_to_blocks[size].insert(start);
    }
  }

  for (int result : results) {
    cout << result << "\n";
  }

  return 0;
}