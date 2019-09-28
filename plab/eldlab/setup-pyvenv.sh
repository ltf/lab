#!/usr/bin/env bash
python3.6 -m venv venv
source venv/bin/activate
pip install --upgrade pip
pip install numpy
pip install pandas
pip freeze > venv/requirements.txt
deactivate






