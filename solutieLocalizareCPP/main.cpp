#include <plant.h>
#include <vector>
#include <iostream>

using namespace std;

int main() {
    Plant x;

    vector<pair<double, double> > p;

    p.push_back({0.5, 0.5});
    p.push_back({0.5, 2.0});
    p.push_back({2.0, 2.0});
    p.push_back({2.0, 0.5});

    x.SetPoints(p);

    cout << x.Contains(1.0, 1.0) << '\n';
    cout << x.Contains(1.0, 0.0) << '\n';

    return 0;
}
