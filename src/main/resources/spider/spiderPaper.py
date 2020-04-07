# -*- coding: utf-8 -*-
from threading import Thread
import time
import json
import re
import requests

USER_AGENT = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 ' \
             'Safari/537.36 '

base_path = "C:\\Users\\15535\\Desktop\\spider\\"  # spider所在目录
base_num = '0'
start_num = 10000  # spider开始的文章pdflink


class TimeoutException(Exception):
    pass


ThreadStop = Thread._Thread__stop


def runOther(csv_path, myLinks):
        ase_res = open('ase_res_my.json', 'a')
        with open(csv_path, 'ab+') as f:
            for i in myLinks:
                try:
                    myrun(f, i)
                except TimeoutException as e:
                    print('outtime')
        ase_res.flush()
        ase_res.close()


def timelimited(timeout):
    def decorator(function):
        def decorator2(*args, **kwargs):
            class TimeLimited(Thread):
                def __init__(self, _error=None, ):
                    Thread.__init__(self)
                    self._error = _error

                def run(self):
                    try:
                        self.result = function(*args, **kwargs)
                    except Exception, e:
                        self._error = str(e)

                def _stop(self):
                    if self.isAlive():
                        ThreadStop(self)

            t = TimeLimited()
            t.start()
            t.join(timeout)

            if isinstance(t._error, TimeoutException):
                t._stop()
                raise TimeoutException('timeout for %s' % (repr(function)))

            if t.isAlive():
                t._stop()
                raise TimeoutException('timeout for %s' % (repr(function)))

            if t._error is None:
                return t.result

        return decorator2

    return decorator

def get_keywords(keywords):
    for kds in keywords:
        if kds["type"] == 'IEEE Keywords':
            return kds["kwd"]
    return []


def get_keywords1(keywords):
    for kds in keywords:
        if kds["type"] == 'INSPEC':
            return kds["kwd"]
    return []


def get_keywords2(keywords):
    for kds in keywords:
        if kds["type"] == 'INSPEC: Non-Controlled Indexing':
            return kds["kwd"]
    return []


def get_keywords3(keywords):
    for kds in keywords:
        if kds["type"] == 'Author Keywords ':
            return kds["kwd"]
    return []


def get_reference(url, link_num):
    headers = {"Connection": "close", "Accept": "application/json, text/plain, */*", "cache-http-response": "true",
               "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3377.1 Safari/537.36",
               "Referer": "https://ieeexplore.ieee.org/document/" + link_num + "/references",
               "Accept-Encoding": "gzip, deflate",
               "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8"}
    res = requests.get(url, headers=headers)
    content = json.loads(res.text)
    if 'references' in content:
        reference = content["references"]
    else:
        reference = list()
    return reference


def get_keywords4(keywords):
    for kds in keywords:
        if kds["type"] == 'INSPEC: Controlled Indexing':
            return kds["kwd"]
    return []


def ieee_info(url):
    headers = {'User-Agent': USER_AGENT}
    res = requests.get(url, headers=headers)
    pattern = re.compile('metadata={.*};')
    content = json.loads(pattern.search(res.text).group()[9:-1])
    # for i in content:
    #     print(i)
    #     print(content[str(i)])
    if 'title' in content:
        title = content['title']
    else:
        title = ''
    if 'publicationYear' in content:
        publicationYear = content['publicationYear']
    else:
        publicationYear = ''
    if 'authors' in content:
        authors = content['authors']
    else:
        authors = list()

    if 'publicationTitle' in content:
        publication = content['publicationTitle']
    else:
        publication = ''

    if 'keywords' in content:
        keywords = content['keywords']
    else:
        keywords = ''
    # ____________________________
    if 'startPage' in content:
        startPage = content['startPage']
    else:
        startPage = ''
    if 'endPage' in content:
        endPage = content['endPage']
    else:
        endPage = ''
    if 'publicationYear' in content:
        publicationYear = content['publicationYear']
    else:
        publicationYear = ''
    if 'citationCount' in content:
        citationCount = content['citationCount']
    else:
        citationCount = ''
    if 'volume' in content:
        volume = content['volume']
    else:
        volume = ''
    if 'issue' in content:
        issue = content['issue']
    else:
        issue = ''
    if 'issn' in content:
        issn = content['issn']
    else:
        issn = ''
    if 'abstract' in content:
        abstract = content['abstract']
    else:
        abstract = ''
    # ___________________________________
    if 'pdfLink' in content:
        pdfLink = content['pdfLink']
    else:
        pdfLink = ''
    if 'doi' in content:
        doi = content['doi']
    else:
        doi = ''
    if 'onlineDate' in content:
        onlineDate = content['onlineDate']
    else:
        onlineDate = ''
    if 'publisher' in content:
        publisher = content['publisher']
    else:
        publisher = ''
    # "Document Title",Authors,"Author Affiliations","Publication Title",Date Added To Xplore,
    # "Publication Year","Volume","Issue","Start Page","End Page","Abstract","ISSN",ISBNs,"DOI",
    # Funding Information,PDF Link,"Author Keywords","IEEE Terms","INSPEC Controlled Terms",
    # "INSPEC Non-Controlled Terms","Mesh_Terms",Article Citation Count,"Reference Count","License",
    # "Online Date",Issue Date,"Meeting Date","Publisher",Document Identifier

    paper = dict(
        title=title.encode('utf-8'),
        publicationYear=publicationYear,
        authors=authors,
        publication=publication,
        keywords=keywords,
        pdfLink=pdfLink,
        volume=volume,
        issue=issue,
        abstract=abstract,
        issn=issn,
        startPage=startPage,
        endPage=endPage,
        doi=doi,
        ArticleCitationCount=citationCount,
        Reference_Count='',
        onlineDate=onlineDate,
        publisher=publisher,
    )
    return paper


@timelimited(10)
def myrun(f, a):
    try:
        print(a + " start...")
        url = a
        paper = ieee_info(url)

        if paper['title'] == '':
            print('none')

        else:
            Authors = ''
            Author_Affiliations = ''
            Keywords = ''
            INSPEC = ''
            INSPECN = ''
            aukey = ''
            asdf = ''
            a00 = 0
            for i in paper['authors']:
                a00 = a00 + 1
                if a00 == len(paper['authors']):
                    ss1 = i['firstName'].split(' ')
                    for j in ss1:
                        Authors = Authors + j[0] + '. '
                    Authors = Authors + i['lastName']
                    Author_Affiliations = Author_Affiliations + i['affiliation']
                else:
                    ss1 = i['firstName'].split(' ')
                    for j in ss1:
                        Authors = Authors + j[0] + '. '
                    Authors = Authors + i['lastName'] + '; '
                    Author_Affiliations = Author_Affiliations + i['affiliation'] + "; "
            b00 = 0
            Keywords0 = get_keywords(paper['keywords'])
            for i in Keywords0:
                b00 = b00 + 1
                if b00 == len(Keywords0):
                    Keywords = Keywords + i
                else:
                    Keywords = Keywords + i + ";"
            Keywords0 = get_keywords1(paper['keywords'])
            b00 = 0
            for i in Keywords0:
                b100 = b00 + 1
                if b00 == len(Keywords0):
                    INSPEC = INSPEC + i
                else:
                    INSPEC = INSPEC + i + ";"
            Keywords0 = get_keywords2(paper['keywords'])
            b00 = 0
            for i in Keywords0:
                b00 = b00 + 1
                if b00 == len(Keywords0):
                    INSPECN = INSPECN + i
                else:
                    INSPECN = INSPECN + i + ";"
            Keywords0 = get_keywords3(paper['keywords'])
            b00 = 0
            for i in Keywords0:
                b00 = b00 + 1
                if b00 == len(Keywords0):
                    aukey = aukey + i
                else:
                    aukey = aukey + i + ";"
            Keywords0 = get_keywords4(paper['keywords'])
            b00 = 0
            for i in Keywords0:
                b00 = b00 + 1
                if b00 == len(Keywords0):
                    asdf = asdf + i
                else:
                    asdf = asdf + i + ";"
            apd = ""
            paper2 = dict(
                Document_Title="\"" + (paper['title'].encode('utf8')) + "\",",
                Authors="\"" + Authors.encode('utf8') + "\",",
                Author_Affiliations="\"" + Author_Affiliations.encode('utf8') + "\",",
                publication_title="\"" + paper['publication'].encode('utf8') + "\",",
                DateAddedToXplore="\"" + apd.encode('utf8') + "\",",
                publicationYear="\"" + paper['publicationYear'].encode('utf8') + "\",",
                volume="\"" + paper['volume'].encode('utf8') + "\",",
                issue="\"" + paper['issue'].encode('utf8') + "\",",
                startPage="\"" + paper['startPage'].encode('utf8') + "\",",
                endPage="\"" + paper['endPage'].encode('utf8') + "\",",
                abstract="\"" + apd.encode('utf8') + "\",",
                issn="\"" + apd.encode('utf8') + "\",",
                ISBNs="\"" + apd.encode('utf8') + "\",",
                doi="\"" + paper['doi'].encode('utf8') + "\",",
                FundingInformation="\"" + apd.encode('utf8') + "\",",
                pdfLink="\"" + ('https://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=' + str(a)).encode(
                    'utf8') + "\",",
                IEEE_Terms="\"" + Keywords.encode('utf8') + "\",",
                keywords="\"" + aukey.encode('utf8') + "\",",
                INSPEC_Controlled_Terms="\"" + asdf.encode('utf8') + "\",",
                INSPEC_Non_Controlled_Terms="\"" + INSPECN.encode('utf8') + "\",",
                Mesh_Terms="\"" + apd.encode('utf8') + "\",",
                ArticleCitationCount="\"" + paper['ArticleCitationCount'].encode('utf8') + "\",",
                Reference_Count="\"" + apd.encode('utf8') + "\",",
                License="\"" + apd.encode('utf8') + "\",",
                onlineDate="\"" + paper['onlineDate'].encode('utf8') + "\",",
                IssueDate="\"" + apd.encode('utf8') + "\",",
                Meeting_Date="\"" + apd.encode('utf8') + "\",",
                publisher="\"" + paper['publisher'].encode('utf8') + "\",",
                DocumentIdentifier="\"" + "IEEE Conferences" + "\"",
            )
            ss = paper2['Document_Title'] + paper2['Authors'] + paper2['Author_Affiliations'] + paper2[
                'publication_title'] + paper2['DateAddedToXplore'] + paper2['publicationYear'] + paper2[
                     'volume'] + paper2['issue'] + paper2['startPage'] + paper2['endPage'] + paper2[
                     'abstract'] + paper2['issn'] + paper2['ISBNs'] + paper2['doi'] + paper2[
                     'FundingInformation'] + paper2['pdfLink'] + paper2['keywords'] + paper2['IEEE_Terms'] + \
                 paper2['INSPEC_Controlled_Terms'] + paper2['INSPEC_Non_Controlled_Terms'] + paper2[
                     'Mesh_Terms'] + paper2['ArticleCitationCount'] + paper2['Reference_Count'] + paper2[
                     'License'] + paper2['onlineDate'] + paper2['IssueDate'] + paper2['Meeting_Date'] + paper2[
                     'publisher'] + paper2['DocumentIdentifier']
            if ss[0] == '\"':
                f.write(ss)
                f.write('\r\n')
            f.flush()
    except Exception:
        print("error")


def ieee_parse(csv_path):
    # ase
    ase_res = open(base_path + 'ase_res' + base_num + '.json', 'a')
    # icse
    # icse_res = open(base_path + 'icse_res.json', 'a')

    res = []

    # flag = False
    a = start_num
    with open(csv_path, 'ab+') as f:
        while a < 10000000:
            try:
                myrun(f, a)
                a = a + 1
            except TimeoutException as e:
                print('outtime')
    ase_res.flush()
    ase_res.close()


def format_reference(ref):
    print(ref)
    res = list()
    for s in ref:
        if len(s.strip()) == 0:
            pass
        else:
            res.append(s)
    return res


def do_something_after_timeout(f, a):
    print('outtime')



