# -*- coding: utf-8 -*-
import requests
from bs4 import BeautifulSoup
from selenium import webdriver
import time
#python 版本2.7 java1.8 需要安装bs4和selenium包
import  spiderPaper
#你的csv文件所在地上级目录
base_path = "C:\\Users\\15535\\Desktop\\spider\\"
queryText="nju"#搜索的关键词
url = 'https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText='+queryText+'&highlight=true&returnFacets=ALL&returnType=SEARCH&pageNumber='
# 设置你需要的文章数量，一页25条 比如2表示你需要50-75篇文章
maxPageNumber = 1
# 执行函数
def work(browser,url):

    browser.get(url)
    ele_nums = []
    time.sleep(10)
    js = 'window.scrollTo(0, document.body.scrollHeight);'
    browser.execute_script(js)
    time.sleep(5)
    browser.execute_script(js)

    try:
        for link in browser.find_elements_by_xpath("//*[@data-artnum]"):
            if isContainClass(link.get_attribute('className'),'icon-pdf'):
                ele_num = link.get_attribute('data-artnum')
                ele_nums.append(ele_num)

        return ele_nums

    except:
        print("failure")


#用于判断某元素是否具有某class
def isContainClass(allClass,targetClass):
    #解析allClass,判断是否包含targetClass
    classArr = allClass.split(' ')
    result = False
    for str in classArr:
        if str == targetClass:
            result = True
            break
    return result


def getHtml(url):
    # Mozilla/5.0 (Windows NT 10.0; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0
    headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0'}
    try:
        response = requests.get(url,timeout=40,headers=headers)
        response.raise_for_status()

        response.encoding = response.apparent_encoding

        return response.text
    except:
        import traceback
        traceback.print_exc()

def getSoup(html):
    soup = BeautifulSoup(html,'html.parser')
    print(soup.body.find_all('a',attrs={'class':r'icon-pdf'}))

def downloadPaper(url):
    try:
        soup = BeautifulSoup(getHtml(url), 'html.parser')
        result = soup.body.find_all('iframe')

        downloadUrl = result[-1].attrs['src'].split('?')[0]

        headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0'}
        response = requests.get(downloadUrl, timeout=80, headers=headers)

        fname = downloadUrl[-12:]
        print(fname)

        with open(fname,'ab+') as f:
            print('start download file ',fname)
            f.write(response.content)
    except:
        import traceback
        with open('errorLog','ab+') as f:
            traceback.print_exc(file=f)

if __name__ == '__main__':
#要先在这个目录下创建一个ase1.csv文件
    csv_p=base_path+'ase1.csv'
    baseUrl = 'https://ieeexplore.ieee.org/document/'
    browser = webdriver.Chrome()
    ki=1
    links=[]
    while ki<=maxPageNumber:
        url1=url+str(ki)
        print("asdfasdf")
        eleNums = work(browser,url1)
        eleNums = list(set(eleNums))
        for eleNum in eleNums:
            newUrl = baseUrl+str(eleNum)
            print(newUrl)
            links.append(newUrl)
        ki=ki+1
    spiderPaper.runOther(csv_p,links)
