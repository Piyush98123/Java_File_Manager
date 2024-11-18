package org.example;

import org.example.modal.FileInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadThread extends Thread{

    private FileInfo file;

    DownloadManager manager;

    DownloadThread(DownloadManager dm, FileInfo f){
        manager=dm;
        file=f;
    }


    @Override
    public void run(){
        //download logic
        this.file.setStatus("DOWNLOADING");
        this.manager.updateUI(file);
        try {
            Files.copy(new URL(this.file.getUrl()).openStream(), Paths.get(file.getPath()));
            this.file.setStatus("DONE");
            this.file.setAction("OPEN");
        } catch (IOException e) {
            this.file.setStatus("FAILED");
            this.file.setAction("FAILED");
            System.out.println("Downloading error");
            e.printStackTrace();
        }
        this.manager.updateUI(file);

    }
}
