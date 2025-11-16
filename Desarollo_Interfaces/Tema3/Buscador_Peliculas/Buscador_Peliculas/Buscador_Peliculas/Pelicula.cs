using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Buscador_Peliculas
{
    public class Pelicula : INotifyPropertyChanged
    {
        // Atributos
        private int id;
        private string titulo = string.Empty;
        private string director = string.Empty;
        private int year;
        private int vista;

        [Key]
        public int Id
        {
            get {  return id; }
            set
            {
                id = value;
                // Llamamos a la funcion OnPropertyChanged cuando esta sufre un cambio
                OnPropertyChanged();
            }
        }

        [Required]
        [MaxLength(30)]
        public string Titulo
        {
            get { return  titulo; }
            set
            {
                titulo = value;
                // Llamamos a la funcion OnPropertyChanged cuando esta sufre un cambio
                OnPropertyChanged();
            }
        }

        [Required]
        [MaxLength(30)]
        public string Director
        {
            get { return  director; }
            set
            {
                director = value;
                // Llamamos a la funcion OnPropertyChanged cuando esta sufre un cambio
                OnPropertyChanged();
            }
        }

        public int Year
        {
            get { return  year; }
            set
            {
                year = value;
                // Llamamos a la funcion OnPropertyChanged cuando esta sufre un cambio
                OnPropertyChanged();
            }
        }

        [Range(0, 1)]
        public int Vista
        {
            get { return  vista; }
            set
            {
                vista = value;
                // Llamamos a la funcion OnPropertyChanged cuando esta sufre un cambio
                OnPropertyChanged();
            }
        }

        // Implementación de la interfaz INotifyPropertyChanged

        public event PropertyChangedEventHandler? PropertyChanged;

        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
