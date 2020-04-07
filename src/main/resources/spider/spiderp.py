import requests
from bs4 import BeautifulSoup
from selenium import webdriver
import time

from selenium.webdriver.common.keys import Keys

browser = webdriver.Chrome()
i=2009
while i <=2020:
    try:
#
        y=str(i)
        stt=time.time()
        print("Year "+y+' start.....')
        url = 'https://ieeexplore.ieee.org/search/searchresult.jsp?highlight=true&returnType=SEARCH&sortType=most-popular&returnFacets=ALL&ranges=' + y + '_' + y + '_Year'
        browser.get(url)
        time.sleep(15)
        js = "window.open('http://www.sogou.com')"
        browser.get('https://ieeexplore.ieee.org/search/searchExport.jsp')
        browser.execute_script(js)
        edt=time.time()
        print('cost: '+str(stt-edt))
    except Exception as e:
        print('error')
    i=i+1
