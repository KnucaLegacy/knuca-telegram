package com.theopus.schedule.backend.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;

public class Storage {

    private final RussianAnalyzer analyzer;
    private Directory writeDirectory;
    private Directory readDirectory;
    private IndexWriter writer;
    private final QueryParser queryParser;
    private IndexReader reader;
    private IndexSearcher searcher;

    public Storage() throws IOException {
        //final
        analyzer = new RussianAnalyzer();
        queryParser = new QueryParser("name", analyzer);
        queryParser.setAllowLeadingWildcard(true);

        this.readDirectory = new RAMDirectory();
        IndexWriter touch = new IndexWriter(readDirectory, config());
        //pre touch
        touch.commit();
        touch.flush();
        touch.close();

        this.writeDirectory = new RAMDirectory();
        init();
    }

    public void close() throws IOException {
        writeDirectory.close();
        readDirectory.close();
        writer.close();
        reader.close();
        analyzer.close();
    }

    private IndexWriterConfig config() {
        return new IndexWriterConfig(analyzer);
    }

    public void emptyWrite() throws IOException {
        this.writeDirectory.close();
        this.writeDirectory = new RAMDirectory();
        initWriter();
    }

    public void init() throws IOException {
        if (writer != null) {
            writer.close();
        }
        initWriter();
        if (reader != null) {
            reader.close();
        }
        this.reader = DirectoryReader.open(readDirectory);
        this.searcher = new IndexSearcher(reader);
    }

    public void initWriter() throws IOException {
        this.writer = new IndexWriter(writeDirectory, config());
        //pre touch
        writer.commit();
        writer.flush();
    }


    public void swap() throws IOException {
        Directory tmp = writeDirectory;
        writeDirectory = readDirectory;
        readDirectory = tmp;
        init();
    }


    public void storeGroups(Collection<Group> groups) throws IOException {
        writer.addDocuments(groups.stream()
                .map(group -> create(group.getId(), group.getName(), SearchResultType.GROUP))
                .collect(Collectors.toList()));
        writer.commit();
        writer.flush();
    }

    public void storeTeachers(Collection<Teacher> teachers) throws IOException {
        writer.addDocuments(teachers.stream()
                .map(teacher -> create(teacher.getId(), teacher.getName(), SearchResultType.TEACHER))
                .collect(Collectors.toList()));
        writer.commit();
        writer.flush();
    }

    public void storeRooms(Collection<Room> rooms) throws IOException {
        writer.addDocuments(rooms.stream()
                .map(room -> create(room.getId(), room.getName(), SearchResultType.ROOM))
                .collect(Collectors.toList()));
        writer.commit();
        writer.flush();

    }

    public List<SearchResult> search(String text) {
        try {
//            this.reader = DirectoryReader.open(readDirectory);
//            this.searcher = new IndexSearcher(reader);

            Query query = queryParser.parse(text);
            TopDocs search = searcher.search(query, 1000);
            return toResults(search);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SearchResult> toResults(TopDocs search) throws IOException {
        List<SearchResult> result = new ArrayList<>();

        for (ScoreDoc scoreDoc : search.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            result.add(new SearchResult(Long.parseLong(doc.get("id")), doc.get("name"), SearchResultType.valueOf(doc.get("type"))));
        }

        return result;
    }

    private Document create(Long id, String name, SearchResultType type) {
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(id), Field.Store.YES));
        doc.add(new TextField("name", name, Field.Store.YES));
        doc.add(new StringField("type", type.name(), Field.Store.YES));
        return doc;
    }

    public enum SearchResultType {
        ROOM,
        TEACHER,
        GROUP
    }

    public static class SearchResult {
        public final Long id;
        public final String name;
        public final SearchResultType type;

        public SearchResult(Long id, String name, SearchResultType type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return "SearchResult{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
