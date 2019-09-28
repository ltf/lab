import numpy as np
import pandas as pd
from pandas import Series, DataFrame


##data = np.loadtxt("/Users/f/downloads/report(2).csv")


data = pd.read_csv("/Users/f/downloads/report(2).csv")

data.sort_values(by="status")


print(data.get_value())









