# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

if (${ANDROID_ABI} STREQUAL "x86_64")
    include_directories(${ANDROID_SYSROOT}/usr/include/x86_64-linux-android)
elseif (${ANDROID_ABI} STREQUAL "x86")
    include_directories(${ANDROID_SYSROOT}/usr/include/i686-linux-android)
elseif (${ANDROID_ABI} STREQUAL "arm64-v8a")
    include_directories(${ANDROID_SYSROOT}/usr/include/aarch64-linux-android)
elseif (${ANDROID_ABI} STREQUAL "armeabi-v7a")
    include_directories(${ANDROID_SYSROOT}/usr/include/arm-linux-androideabi)
endif()

#SET(CMAKE_CXX_FLAGS "-isystem /home/cc/Android/Sdk/ndk-bundle/sysroot/usr/include/arm-linux-androideabi")

SET(CMAKE_CXX_FLAGS "-Wall -Wno-unused-variable -g -fno-strict-aliasing")
SET(CMAKE_VERBOSE_MAKEFILE on)
SET(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

include_directories (
	"src/main/jni"
	"src/main/jni/tts/text"
	"src/main/jni/tts/common"
	"src/main/jni/tts/manager"
	"src/main/jni/tts/synthesis/hts_engine"
       )

add_library( # Sets the name of the library.
             TTS

             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             src/main/jni/libTTS.cpp
             src/main/jni/tts/text/TextProcess.cpp
             src/main/jni/tts/common/CString.cpp
             src/main/jni/tts/text/Word.cpp
             src/main/jni/tts/text/WordInfo.cpp
             src/main/jni/tts/text/Phone.cpp
             )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


target_link_libraries( # Specifies the target library.
                       TTS

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )


add_subdirectory(src/main/jni/tts/synthesis/hts_engine)


############## 備註 ###############################################
# 指定編譯參數，可選
#SET(CMAKE_CXX_FLAGS "-Wno-error=format-security -Wno-error=pointer-sign")
#SET(CMAKE_CXX_FLAGS "-Wall -Wno-unused-variable -g -fno-strict-aliasing")
#設置生成的so動態庫最後輸出的路徑
#set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

#設置頭文件搜索路徑（和此txt同個路徑的頭文件無需設置），可選
#INCLUDE_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR}/common)

#指定用到的系統庫或者NDK庫或者第三方庫的搜索路徑，可選。
#LINK_DIRECTORIES(/usr/local/lib)