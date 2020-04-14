#include "plant.h"

using namespace std;

void Plant::SetPoints(vector<pair <double, double> > &points)
{
    coordinates = points;
    /// PUNCTELE TREBUIE DATE INTR-O DIRECTIE: FIE CLOCKWISE, FIE COUNTER-CLOCKWISE
}

bool Plant::Contains(double x, double y)
{
    if(coordinates.size() < 2) {
        return false;
    }

    pair<double, double> myPoint = {x, y};
    pair<double, double> myPoint2 = {0, 0};
    pair<double, double> myPoint3 = {0, 1};
    int countSegments2 = 0;
    int countSegments3 = 0;
    for(unsigned int i = 0; i < coordinates.size(); i++) {
        pair<double, double> poly1 = coordinates.at(i);
        pair<double, double> poly2 = coordinates.at(NextPoint(i));

        countSegments2 += doIntersect(poly1, poly2, myPoint, myPoint2);
        countSegments3 += doIntersect(poly1, poly2, myPoint, myPoint3);
    }

    return (countSegments2 % 2 == 1 || countSegments3 % 2 == 1);
}

int Plant::NextPoint(int index)
{
    if(coordinates.size() == 0) {
        return 0;
    }

    return (index + 1) % coordinates.size();
}

bool Plant::onSegment(pair<double, double> p, pair<double, double> q, pair<double, double> r)
{
    if (q.first <= max(p.first, r.first) && q.first >= min(p.first, r.first) &&
        q.second <= max(p.second, r.second) && q.second >= min(p.second, r.second))
       return true;

    return false;
}

// To find orientation of ordered triplet (p, q, r).
// The function returns following values
// 0 --> p, q and r are colinear
// 1 --> Clockwise
// 2 --> Counterclockwise
int Plant::orientation(pair<double, double> p, pair<double, double> q, pair<double, double> r)
{
    // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
    // for details of below formula.
    double val = (q.second - p.second) * (r.first - q.first) -
              (q.first - p.first) * (r.second - q.second);

    if (val < 0.0001) return 0;  // colinear

    return (val > 0)? 1: 2; // clock or counterclock wise
}

bool Plant::doIntersect(pair<double, double> p1, pair<double, double> q1, pair<double, double> p2, pair<double, double> q2)
{
    // Find the four orientations needed for general and
    // special cases
    int o1 = orientation(p1, q1, p2);
    int o2 = orientation(p1, q1, q2);
    int o3 = orientation(p2, q2, p1);
    int o4 = orientation(p2, q2, q1);

    // General case
    if (o1 != o2 && o3 != o4)
        return true;

    // Special Cases
    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
    if (o1 == 0 && onSegment(p1, p2, q1)) return true;

    // p1, q1 and q2 are colinear and q2 lies on segment p1q1
    if (o2 == 0 && onSegment(p1, q2, q1)) return true;

    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
    if (o3 == 0 && onSegment(p2, p1, q2)) return true;

     // p2, q2 and q1 are colinear and q1 lies on segment p2q2
    if (o4 == 0 && onSegment(p2, q1, q2)) return true;

    return false; // Doesn't fall in any of the above cases
}
