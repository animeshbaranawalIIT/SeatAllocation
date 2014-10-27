#!/bin/bash

sed -f change.sed test.cpp | sed -f rm_comments.sed | sed -nf rm_useless.sed >test_final.cpp
