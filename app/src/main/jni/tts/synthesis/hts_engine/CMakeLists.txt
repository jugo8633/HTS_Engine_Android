cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})


include_directories (
	"include"
	"lib"
       )

add_library (hts_engine 
	SHARED
	lib/HTS_audio.c
	lib/HTS_engine.c
	lib/HTS_gstream.c
	lib/HTS_label.c
	lib/HTS_misc.c
	lib/HTS_model.c
	lib/HTS_pstream.c
	lib/HTS_sstream.c
	lib/HTS_vocoder.c
	)

