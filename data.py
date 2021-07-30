import os
import re
import csv

import nltk
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer, HashingVectorizer, CountVectorizer
from sklearn.feature_extraction import DictVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import cross_val_score
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from sklearn import svm
from sklearn.preprocessing import normalize, LabelEncoder

import warnings

warnings.filterwarnings('ignore')

# from gensim.models import Word2Vec

from scipy import sparse
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import MinMaxScaler
from matplotlib import gridspec
import matplotlib.pyplot as plt
from sklearn import tree
from sklearn.datasets import load_wine
from sklearn.model_selection import train_test_split

data_dict = {}
s1 = "Facts"
s2 = "Ruling by Lower Court"
s3 = "Argument"
s4 = "Statute"
s5 = "Precedent"
s6 = "Ratio of the decision"
s7 = "Ruling by Present Court"
#   Fact                              p1
#   Ruling by Lower Court             p2
#   Argument                          p3
#   Statute                           p4
#   Precedent                         p5
#   Ratio of the decision             p6
#   Ruling by Present Court           p7
p1 = []
p2 = []
p3 = []
p4 = []
p5 = []
p6 = []
p7 = []
p8 = []

files = os.listdir('D:\\FileRecv\\aila20-task2\\task-2')

for file in files:
    if not os.path.isdir(file):
        with open('D:\\FileRecv\\aila20-task2\\task-2\\' + file, encoding='utf-8') as f:
            for line in f.readlines():
                line = line.replace("\n", '')
                x = line.split('\t')
                match = re.compile("[^a-z^A-Z^0-9]")
                x[1] = match.sub(' ', x[1])
                x[1] = x[1].strip()
                if x[1] == s1:
                    p1.append(x[0])
                elif x[1] == s2:
                    p2.append(x[0])
                elif x[1] == s3:
                    p3.append(x[0])
                elif x[1] == s4:
                    p4.append(x[0])
                elif x[1] == s5:
                    p5.append(x[0])
                elif x[1] == s6:
                    p6.append(x[0])
                elif x[1] == s7:
                    p7.append(x[0])

data_dict = {
    s1: p1,
    s2: p2,
    s3: p3,
    s4: p4,
    s5: p5,
    s6: p6,
    s7: p7
}

test_file = open(r'C:\Users\Me\Desktop\task2\data\test.tsv', 'a', encoding='utf-8')
valid_file = open(r'C:\Users\Me\Desktop\task2\data\valid.tsv', 'a', encoding='utf-8')
train_file = open(r'C:\Users\Me\Desktop\task2\data\train.tsv', 'a', encoding='utf-8')
for key in data_dict:
    p_list = data_dict[key]
    for text_i in range(len(p_list)):
        write_str = key + '   ' + p_list[text_i]
        if text_i <= len(p_list)*0.1:
            valid_file.write(write_str+'\n')
        elif text_i <= len(p_list)*0.3:
            test_file.write(write_str+'\n')
        else:
            train_file.write(write_str+'\n')

test_file.close()
valid_file.close()
train_file.close()

# train_X = []  # content
# train_Y = []
# test_X = []
# test_Y = []
#
# for k in data_dict.keys():
#     for i, value in enumerate(data_dict[k]):
#         if i % 5 != 0:
#             new_value = re.sub('[^\w ]', '', value)
#             train_file = open('train_data.csv', 'a', encoding='utf-8',newline='')
#             csv_writer = csv.writer(train_file)
#             csv_writer.writerow([value, k])
#
#         else:
#             test_file = open('test_data.csv', 'a', encoding='utf-8', newline='')
#             csv_writer = csv.writer(test_file)
#             csv_writer.writerow([value, k])

