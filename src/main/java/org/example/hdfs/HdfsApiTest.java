package org.example.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.net.URI;

public class HdfsApiTest {
    @Test
    public void getFileSystem() throws Exception{
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"),new Configuration(),"root");
        boolean  f = fileSystem.mkdirs(new Path("/xxx/yyy"));
        System.out.println(f);
    }
}
