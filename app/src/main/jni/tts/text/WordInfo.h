#pragma once

#include "Type.h"
//注意：WA_VA11 ~ WA_VP : 不要更動其順序
enum WORD_ATTR {
	WA_NONE = 0,WA_NUM,	WA_NAME, WA_VA11, WA_VA12,
      WA_VA13, WA_VA2,	WA_VA3,  WA_VA4,  WA_VB11,
      WA_VB12, WA_VB2,  WA_VC1,  WA_VC2,  WA_VC31,
      WA_VC32, WA_VC33, WA_VD1,  WA_VD2,  WA_VE11,
      WA_VE12, WA_VE2,  WA_VF1,  WA_VF2,  WA_VG1,
      WA_VG2,  WA_VH11, WA_VH12, WA_VH13, WA_VH14,
      WA_VH15, WA_VH16, WA_VH17, WA_VH21, WA_VH22,
      WA_VI1,  WA_VI2,  WA_VI3,  WA_VJ1,  WA_VJ2,
      WA_VJ3,  WA_VK1,  WA_VK2,  WA_VL1,  WA_VL2,
      WA_VL3,  WA_VL4,  WA_V_11, WA_V_12, WA_V_2,
      WA_VP,   WA_VR,   WA_N,    WA_Naa,  WA_Nab,  
	  WA_Nac,  WA_Nad,  WA_Naea, WA_Naeb, WA_Nba,  
	  WA_Nbc,  WA_Nca,  WA_Ncb,  WA_Ncc,  WA_Ncda, 
	  WA_Ncdb, WA_Nce,  WA_Ndaaa,WA_Ndaab,WA_Ndaac,
	  WA_Ndaad,WA_Ndaba,WA_Ndabb,WA_Ndabc,WA_Ndabd,
	  WA_Ndabe,WA_Ndabf,WA_Nhaca,WA_Nhacb,WA_Ndbb, 
	  WA_Ndc,  WA_Ndda, WA_Nddb, WA_Nddc, WA_Ne,   
	  WA_Nfa,  WA_Nfb,  WA_Nfc,  WA_Nfd,  WA_Nfe,  
	  WA_Nff,  WA_Nfg,  WA_Nfh,  WA_Nfi,  WA_Nfzz, 
	  WA_Ng,   WA_Nhaa, WA_Nhab, WA_Nhac, WA_Nhb,  
	  WA_Nhc,  WA_NP,   WA_P1,   WA_P2,   WA_P3,   
	  WA_P4,   WA_P5,   WA_P6,   WA_P7,   WA_P8,   
	  WA_P9,   WA_P10,  WA_P11,  WA_P12,  WA_P13,  
	  WA_P14,  WA_P15,  WA_P16,  WA_P17,  WA_P18,  
	  WA_P19,  WA_P20,  WA_P21,  WA_P22,  WA_P23,  
	  WA_P24,  WA_P25,  WA_P26,  WA_P27,  WA_P28,  
	  WA_P29,  WA_P30,  WA_P31,  WA_P32,  WA_P33,  
	  WA_P34,  WA_P35,  WA_P36,  WA_P37,  WA_P38,  
	  WA_P39,  WA_P40,  WA_P41,  WA_P42,  WA_P43,  
	  WA_P44,  WA_P45,  WA_P46,  WA_P47,  WA_P48,  
	  WA_P49,  WA_P50,  WA_P51,  WA_P52,  WA_P53,  
	  WA_P54,  WA_P55,  WA_P56,  WA_P57,  WA_P58,  
	  WA_P59,  WA_P60,  WA_P61,  WA_P62,  WA_P63,  
	  WA_P64,  WA_P65,  WA_A,    WA_b,    WA_Caa,  
	  WA_Cab,  WA_Cbaa, WA_Cbab, WA_Cbba, WA_Cbbb, 
	  WA_Cbca, WA_Cbcb, WA_De,   WA_Dia,  WA_I,    
	  WA_RD,   WA_S,    WA_Str,  WA_T,    WA_Ta,   
	  WA_Tb,   WA_Tc,   WA_Td,   WA_Da,   WA_Dbaa, 
	  WA_Dbab, WA_Dbb,  WA_Dbc,  WA_Dc,   WA_Dd,   
	  WA_Dfa,  WA_Dfb,  WA_Dg,   WA_Dh,   WA_Di,   
	  WA_Dj,   WA_Dk,   WA_V_LY, WA_M,    WA_STAR
};

#define WORD_ATTR_NUM (WA_STAR+1)
//最大的詞數
#define WORD_LEN 10
#define WORD_LEN2 (WORD_LEN*2)

#pragma pack(push,1)
struct WORD_DB  //總共占 48 bytes (仲)
{
	char byte;				//詞的BYTE數 (1 byte)
	UCHAR attr[4];          //(4 bytes)
	UCHAR big5[WORD_LEN2];  //big5,一字二占2 byte (20 bytes)
	UINT  counter;          //字數*2 (1 byte)
	short phone[WORD_LEN];	//發音代碼 (10 bytes)
};
#pragma pack(pop)

struct WORD_INFO                //記錄斷詞的結果
{
	char  wlen;					//詞的長度（以中文字為單位)
	UCHAR attr[4];
	UCHAR big5[WORD_LEN2+1];	//+1，當作字串結尾。這是詞的內容(可輸出)
	char  sen_pos;				//詞在句中的位置
	short phone[WORD_LEN];		//發音代碼，也就是sound ID，是將big5轉成sid
};

