using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    public class Pelicula : INotifyPropertyChanged
    {
        // Atributos
        private int id;
        private string titulo = string.Empty;
        private string director = string.Empty;
        private int year;

        [Key]
        public int Id
        {
            get { return id; }
            set { id = value; OnPropertyChanged(); }
        }

        [Required, MaxLength(30)]
        public string Titulo
        {
            get { return titulo; }
            set { titulo = value; OnPropertyChanged(); }
        }

        [Required, MaxLength(30)]
        public string Director
        {
            get { return director; }
            set { director = value; OnPropertyChanged(); }
        }

        public int Year
        {
            get { return year; }
            set { year = value; OnPropertyChanged(); }
        }

        // Relaciones entre tablas

        // FK hacia Cliente (1:N)
        public int? ClienteId { get; set; } // Nullable, porque puede no estar alquilada
        [ForeignKey("ClienteId")]
        public virtual Cliente? Cliente { get; set; }

        // FK hacia Categoría (1:N)
        public int CategoriaId { get; set; }
        [ForeignKey("CategoriaId")]
        public virtual Categoria Categoria { get; set; } = null!;


        // Implementación de la interfaz INotifyPropertyChanged

        public event PropertyChangedEventHandler? PropertyChanged;

        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
