package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.config.AppConfig;
import org.example.modal.FileInfo;

import java.io.File;

public class DownloadManager {


    @FXML
    private TextField urlTextField;

    @FXML
    private TableView<FileInfo> tableView;

    private int index=0;

    String filename;

    @FXML
    void downloadButtonClicked(ActionEvent event) {
        String url = urlTextField.getText().trim();
        filename=url.substring(url.lastIndexOf("/")+1);
        String status = "STARTING";
        String action = "WAIT";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(filename);
        fileChooser.setTitle("Select Location to Save File");
        fileChooser.setInitialDirectory(new File(AppConfig.DOWNLOAD_PATH));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile!=null){
            String path = selectedFile.getAbsolutePath();
            FileInfo file = new FileInfo((index+1)+"", filename, url, status, action, path);
            this.index++;
            DownloadThread downloadThread = new DownloadThread(this, file);
            this.tableView.getItems().add(file);
            downloadThread.start();
        }

    }

    public void updateUI(FileInfo mfile) {
        FileInfo fileInfo=this.tableView.getItems().get(Integer.parseInt(mfile.getIndex())-1);
        fileInfo.setStatus(mfile.getStatus());
        fileInfo.setAction(mfile.getAction());
        this.tableView.refresh();
    }

    @FXML
    public void initialize(){
        // S.NO
        TableColumn<FileInfo, String> sn = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(0);
        sn.setCellValueFactory(table->{
            return table.getValue().indexProperty();
        });

        // File Name
        TableColumn<FileInfo, String> filename = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(1);
        filename.setCellValueFactory(table->{
            return table.getValue().nameProperty();
        });

        // url
        TableColumn<FileInfo, String> url = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(2);
        url.setCellValueFactory(table->{
            return table.getValue().urlProperty();
        });

        // status
        TableColumn<FileInfo, String> status = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(3);
        status.setCellValueFactory(table->{
            return table.getValue().statusProperty();
        });

        // action
        TableColumn<FileInfo, String> action = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(4);
        action.setCellValueFactory(table->{
            return table.getValue().actionProperty();
        });
    }
}
