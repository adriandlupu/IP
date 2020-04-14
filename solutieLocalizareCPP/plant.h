#ifndef PLANT_H
#define PLANT_H

#include <vector>

class Plant
{
    public:
        void SetPoints(std::vector<std::pair<double, double> > &points);
        bool Contains(double x, double y);

    private:
        bool doIntersect(std::pair<double, double> p1, std::pair<double, double> q1, std::pair<double, double> p2, std::pair<double, double> q2);
        bool onSegment(std::pair<double, double> p, std::pair<double, double> q, std::pair<double, double> r);
        int orientation(std::pair<double, double> p, std::pair<double, double> q, std::pair<double, double> r);
        int NextPoint(int index);

        std::vector<std::pair<double, double> > coordinates;
};

#endif // PLANT_H
