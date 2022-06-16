package controller;

import java.io.*;

public class FileWorker {
    String file;

    public FileWorker(String file) {
        this.file = file;
    }

    public void writer(String data) throws IOException, SecurityException {
        try (OutputStream os = new FileOutputStream(file);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {
            writer.write(data);
        } catch (FileNotFoundException e) {
            createFile(file);
            writer(data);
        }
    }

    private void createFile(String file) throws IOException, SecurityException {
        File newFile = new File(file);
        newFile.createNewFile();

    }

    public String reader() {
        return reader(file);
    }

    public String reader(String file) {
        byte[] buffer;
        StringBuilder input = new StringBuilder();
        try (FileInputStream fileInput = new FileInputStream(file);
             BufferedInputStream buffInput = new BufferedInputStream(fileInput)) {

            buffer = new byte[buffInput.available()];
            buffInput.read(buffer, 0, buffInput.available());
            for (byte letter : buffer) {
                input.append((char) letter);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return String.valueOf(input);

    }
}
