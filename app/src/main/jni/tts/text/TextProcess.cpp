//
// Created by jugo on 2018/8/31.
//

#include <android/log.h>
#include <string>
#include "TextProcess.h"
#include "utility.h"
#include "CString.h"

#define VERSION "0.20180831"

using namespace std;

const char *POStags[34] = {" ", "VA", "VC", "VE", "VV", "NR", "NT", "NN", "LC", "PN", "DT", "CD", "OD", "M", "AD", "P", "CC", "CS", "DEC", "DEG", "DER", "DEV", "SP", "AS", "ETC",
                           "MSP", "IJ", "ON", "PU", "JJ", "FW", "LB", "SB", "BA"};

TextProcess::TextProcess()
{

}

TextProcess::~TextProcess()
{

}

string TextProcess::getVersion()
{
    return VERSION;
}

int TextProcess::synthesize(const char *szText, short **ss)
{
    __android_log_print(ANDROID_LOG_DEBUG, "TextProcess", "synthesize text: %s", szText);

    unsigned long ulCount = 0;
    vector<string> vData;
    CString strTemp;
    unsigned long ulIndex;
    unsigned long ulLength;
    bool bAdded = false;
    vector<int> pos; // first: 幾字詞, second: POS tagging
    int j, k;

    //========= 分割字詞 ===========//
    ulCount = spliteData(const_cast<char *>(szText), " ", vData);
    __android_log_print(ANDROID_LOG_DEBUG, "TextProcess", "synthesize spliteData: %lu", ulCount);
    if (0 >= ulCount) {
        return -1;
    }

    //========= 字詞屬性轉換成Index ===========//
    for (vector<string>::iterator it = vData.begin(); vData.end() != it; ++it) {

        __android_log_print(ANDROID_LOG_DEBUG, "TextProcess", "synthesize spliteData: %s", it->c_str());
        ulIndex = it->find("/", 0);
        ulLength = it->length();
        strTemp.format("%s", it->substr(ulIndex + 1, ulLength).c_str());
        __android_log_print(ANDROID_LOG_DEBUG, "TextProcess", "synthesize : %s", strTemp.getBuffer());
        ulIndex /= 2;
        bAdded = false;
        for (j = 1; 34 > j; ++j) {
            if (!strTemp.Compare(POStags[j])) {
                for (int k = 0; k < ulIndex; ++k) {
                    pos.push_back(j);
                    bAdded = true;
                    __android_log_print(ANDROID_LOG_DEBUG, "TextProcess", "pos : %d added", j);
                    break;
                }
            }
        }
        if (!bAdded) {
            for (k = 0; k < ulIndex; ++k) {
                pos.push_back(j);
                __android_log_print(ANDROID_LOG_DEBUG, "TextProcess", "pos : %d", j);
            }
        }

    }


    *ss = new short[1024];
    (*ss)[0] = 11;
    (*ss)[1] = 22;
    return 0;
}

void TextProcess::CartPrediction(CString &sentence, CString &strBig5, std::vector<int> &PWCluster, std::vector<int> &PPCluster)
{
    int featureDim = 14;
    int nCluster = 2;
    vector<int> wordpar;    // 當前的詞長
    vector<int> syllpos;    // 當前的字在詞中位置
    vector<int> cluster;    // 韻律詞結尾=1, otherwise 0
    vector<int> pos;        // first: 幾字詞, second: POS tagging
    vector<int> pwBoundary;
    vector<CString> tempPOSArray;
    int i, j, k;


}
