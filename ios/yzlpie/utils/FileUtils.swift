//
//  FileUtils.swift
//  again
//
//  Created by user on 14/12/6.
//  Copyright (c) 2014年 yzlpie. All rights reserved.
//

import Foundation

struct FileUtils {
    
    //获取Documents目录
    static func dirDoc() -> NSString {
        let path:NSArray = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true) as NSArray
        let documentsDirectory:NSString = path[0] as NSString
        return documentsDirectory
    }

    
    //查找文件
    static func existDTFile() -> Bool{
        let documentPath = FileUtils.dirDoc()
        let fileManager = NSFileManager()
        let targetPath = documentPath.stringByAppendingPathComponent(Constants.dtFile)
        let res = fileManager.fileExistsAtPath(targetPath)
        return res
    }
    
    //写数据到文件
    static func writeFile(content:NSString) -> Bool {
        let documentPath = FileUtils.dirDoc()
        let targetFile = documentPath.stringByAppendingPathComponent(Constants.dtFile)
        let res = content.writeToFile(targetFile, atomically: true, encoding: NSUTF8StringEncoding, error: nil)
        return res
    }
    
    //读文件数据
    static func readFile() -> NSString {
        let documentPath = FileUtils.dirDoc()
        let targetFile = documentPath.stringByAppendingPathComponent(Constants.dtFile)
        let content = NSString(contentsOfFile: targetFile, encoding: NSUTF8StringEncoding, error: nil)
        return content!
    }
    

}