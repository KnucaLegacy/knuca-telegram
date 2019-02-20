package com.theopus;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import com.theopus.entitiy.schedule.Group;
import com.theopus.entitiy.schedule.Room;
import com.theopus.entitiy.schedule.Teacher;
import com.theopus.search.RestRepository;
import com.theopus.search.Storage;
import com.theopus.search.StorageUpdater;

public class Main {

//    public static void main(String[] args) throws IOException, ParseException {
//        Directory directory = new RAMDirectory();
//        Analyzer analyzer = new RussianAnalyzer();
//        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
//        IndexWriter writer = new IndexWriter(directory, conf);
//
//        Document doc = new Document();
//        doc.add(new StringField("id", "1", Field.Store.YES));
//        doc.add(new TextField("name", "цюцюра лел", Field.Store.YES));
//        doc.add(new StringField("type", "TEACHER", Field.Store.YES));
//
//        Document doc2 = new Document();
//        doc2.add(new StringField("id", "2", Field.Store.YES));
//        doc2.add(new TextField("name", "цюцюра чмо", Field.Store.YES));
//        doc2.add(new StringField("type", "GROUP", Field.Store.YES));
//
//        writer.addDocument(doc);
//        writer.addDocument(doc2);
//
//        writer.close();
//
//
//
//        IndexReader reader = DirectoryReader.open(directory);
//        IndexSearcher searcher = new IndexSearcher(reader);
//
//        QueryParser queryParser = new QueryParser("name", new RussianAnalyzer());
//        Query loh = queryParser.parse("цюцюра");
//        TopDocs search = searcher.search(loh, 10);
//
//
//        printResults(loh, searcher, search);
//
//
//    }

    public static void main(String[] args) throws IOException, ParseException {
        Storage storage = new Storage();
        StorageUpdater storageUpdater = new StorageUpdater(
                new RestRepository<>(Group.class, "http://35.233.31.195:8080/groups"),
                new RestRepository<>(Room.class, "http://35.233.31.195:8080/rooms"),
                new RestRepository<>(Teacher.class, "http://35.233.31.195:8080/teachers"),
                storage
        );

        storage.search("Цюцюра").stream().forEach(System.out::println);
    }
}
