#! /usr/bin/env python
# vi:ts=4:et

from setuptools import *

import os, sys
import distutils
from glob import glob
from distutils.core import setup
from distutils.extension import Extension
from distutils.util import split_quoted

include_dirs = [ "lzo-2.03/include" ]
define_macros = []
library_dirs = []
libraries = []
runtime_library_dirs = []
extra_objects = []
extra_compile_args = []
extra_link_args = []

###############################################################################

def get_kw(**kw): return kw

ext = Extension(
    name="lzo",
    sources=["lzomodule.c"]+glob("lzo-2.03/src/lzo*.c"),
    include_dirs=include_dirs,
    define_macros=define_macros,
    library_dirs=library_dirs,
    libraries=libraries,
    runtime_library_dirs=runtime_library_dirs,
    extra_objects=extra_objects,
    extra_compile_args=extra_compile_args,
    extra_link_args=extra_link_args,
)
##print ext.__dict__; sys.exit(1)

setup_args = get_kw(
    name="python-lzo",
    version="1.08_2.03_static",
    description="Python bindings for the LZO data compression library",
    author="Markus F.X.J. Oberhumer",
    author_email="markus@oberhumer.com",
    maintainer="Markus F.X.J. Oberhumer",
    maintainer_email="markus@oberhumer.com",
    url="http://www.oberhumer.com/opensource/lzo/",
    licence="GNU General Public License (GPL)",
    ext_modules=[ext],
    long_description="""
This module provides Python bindings for the LZO data compression library.

LZO is a portable lossless data compression library written in ANSI C.
It offers pretty fast compression and *very* fast decompression.
Decompression requires no memory.

In addition there are slower compression levels achieving a quite
competitive compression ratio while still decompressing at
this very high speed.""",
)

##print distutils.__version__
if distutils.__version__ >= "1.0.2":
    setup_args["platforms"] = "All"

apply(setup, (), setup_args)

