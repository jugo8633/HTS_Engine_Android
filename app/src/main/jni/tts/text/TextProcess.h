//
// Created by jugo on 2018/8/31.
//

#pragma once


#include <string>
#include <vector>

class CString;

class TextProcess
{
public:
    TextProcess();

    virtual ~TextProcess();

    std::string getVersion();

    int synthesize(const char *szText, short **ss);

private:
    void CartPrediction(CString &sentence, CString &strBig5, std::vector<int> &PWCluster, std::vector<int> &PPCluster);

};


