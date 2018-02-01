package fr.lesprogbretons.seawar.utils;

public class TiledCoordinates {
    public int column;
    public int row;

    public TiledCoordinates(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public void setCoords(TiledCoordinates coords) {
        this.column = coords.column;
        this.row = coords.row;
    }

    public void setCoords(int column, int row) {
        this.column = column;
        this.row = row;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.column == ((TiledCoordinates) obj).column && this.row == ((TiledCoordinates) obj).row);
    }
}