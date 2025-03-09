cmake_minimum_required(VERSION 3.25)
project(proj01_lib)

set(CMAKE_CXX_STANDARD 20)
add_library(proj01_lib SHARED library.cpp)
target_link_options(proj01_lib PRIVATE "-static")
target_include_directories(proj01_lib PRIVATE "C:\\Program Files\\Java\\jdk-21\\include")
target_include_directories(proj01_lib PRIVATE "C:\\Program Files\\Java\\jdk-21\\include\\win32")


